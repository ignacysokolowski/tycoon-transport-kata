package domain

import com.natpryce.hamkrest.absent
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.Cargo
import tycoon.transport.domain.CargoId
import tycoon.transport.domain.DeliveryScheduler
import tycoon.transport.domain.Factory
import tycoon.transport.domain.LocationId

class DeliverySchedulerSpy : DeliveryScheduler {
    val cargoesScheduled = mutableListOf<CargoId>()

    override fun scheduleDeliveryOf(cargoId: CargoId) {
        cargoesScheduled.add(cargoId)
    }
}

class FactoryTest {

    private val deliveryScheduler = DeliverySchedulerSpy()
    private val factory = Factory(deliveryScheduler)

    @Test fun `schedules delivery of produced cargoes`() {
        factory.produce(listOf(
            Cargo(CargoId("1"), LocationId("A")),
            Cargo(CargoId("2"), LocationId("A"))
        ))
        assertThat(
            deliveryScheduler.cargoesScheduled,
            equalTo(listOf(CargoId("1"), CargoId("2")))
        )
    }

    @Test fun `loads next cargo on transport when arrives`() {
        factory.produce(listOf(
            Cargo(CargoId("1"), LocationId("A")),
            Cargo(CargoId("2"), LocationId("B"))
        ))
        val transport1 = FakeTransport()
        val transport2 = FakeTransport()
        factory.transportArrived(transport1)
        factory.transportArrived(transport2)
        assertThat(transport1.cargoLoaded, equalTo(CargoId("1")))
        assertThat(transport2.cargoLoaded, equalTo(CargoId("2")))
    }

    @Test fun `does not load cargo if none waiting`() {
        val transport = FakeTransport()
        factory.transportArrived(transport)
        assertThat(transport.cargoLoaded, absent())
    }
}
