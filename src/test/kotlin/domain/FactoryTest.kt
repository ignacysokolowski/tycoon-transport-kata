package domain

import com.natpryce.hamkrest.absent
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.domain.AllCargoPickedUp
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

    @Test fun `has no cargo to be picked up`() {
        assertThrows<AllCargoPickedUp> {
            factory.pickUpNextCargo()
        }
    }

    @Test fun `provides next cargo to pick up`() {
        factory.produce(listOf(
            Cargo(CargoId("1"), LocationId("A")),
            Cargo(CargoId("2"), LocationId("B"))
        ))
        assertThat(factory.pickUpNextCargo(), equalTo(Cargo(CargoId("1"), LocationId("A"))))
        assertThat(factory.pickUpNextCargo(), equalTo(Cargo(CargoId("2"), LocationId("B"))))
    }

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

    @Test fun `loads cargo on transport when arrives`() {
        factory.produce(listOf(
            Cargo(CargoId("1"), LocationId("A"))
        ))
        val transport = FakeTransport()
        factory.transportArrived(transport)
        assertThat(transport.cargoLoaded, equalTo(CargoId("1")))
    }

    @Test fun `does not load cargo if none waiting`() {
        val transport = FakeTransport()
        factory.transportArrived(transport)
        assertThat(transport.cargoLoaded, absent())
    }
}
