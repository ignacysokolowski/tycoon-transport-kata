package domain.delivery

import com.natpryce.hamkrest.absent
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.Location
import tycoon.transport.domain.cargo.Cargo
import tycoon.transport.domain.cargo.CargoId
import tycoon.transport.domain.delivery.DeliveryScheduler
import tycoon.transport.domain.delivery.Factory

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
            Cargo(CargoId("1"), Location("A")),
            Cargo(CargoId("2"), Location("A"))
        ))
        assertThat(
            deliveryScheduler.cargoesScheduled,
            equalTo(listOf(CargoId("1"), CargoId("2")))
        )
    }

    @Test fun `loads next cargo on transport when arrives`() {
        factory.produce(listOf(
            Cargo(CargoId("1"), Location("A")),
            Cargo(CargoId("2"), Location("B"))
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

    @Test fun `puts newly produced cargo on the stack after pickup`() {
        val transport2 = FakeTransport()
        val transport3 = FakeTransport()
        val transport1 = FakeTransport()
        factory.produce(listOf(Cargo(CargoId("1"), Location("A"))))
        factory.produce(listOf(Cargo(CargoId("2"), Location("B"))))
        factory.transportArrived(transport1)
        factory.produce(listOf(Cargo(CargoId("3"), Location("C"))))
        factory.transportArrived(transport2)
        factory.transportArrived(transport3)
        assertThat(transport1.cargoLoaded, equalTo(CargoId("1")))
        assertThat(transport2.cargoLoaded, equalTo(CargoId("2")))
        assertThat(transport3.cargoLoaded, equalTo(CargoId("3")))
    }
}
