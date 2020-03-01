package domain

import com.natpryce.hamkrest.absent
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.domain.AllCargoPickedUp
import tycoon.transport.domain.Cargo
import tycoon.transport.domain.CargoId
import tycoon.transport.domain.CargoNotPickedUp
import tycoon.transport.domain.Factory
import tycoon.transport.domain.LocationId

class FactoryTest {

    private val factory = Factory()

    @Test fun `has all cargo delivered if has not produced any yet`() {
        assertThat(factory.hasAllCargoDelivered(), equalTo(true))
    }

    @Test fun `has no cargo to be picked up`() {
        assertThrows<AllCargoPickedUp> {
            factory.pickUpNextCargo()
        }
    }

    @Test fun `cargo waiting to be picked up are not delivered yet`() {
        factory.produce(listOf(
            Cargo(CargoId("1"), LocationId("A"))
        ))
        assertThat(factory.hasAllCargoDelivered(), equalTo(false))
    }

    @Test fun `can be notified about delivery`() {
        factory.produce(listOf(
            Cargo(CargoId("1"), LocationId("A"))
        ))
        factory.cargoDelivered(factory.pickUpNextCargo().id)
        assertThat(factory.hasAllCargoDelivered(), equalTo(true))
    }

    @Test fun `delivering a single cargo does not deliver all`() {
        factory.produce(listOf(
            Cargo(CargoId("1"), LocationId("A")),
            Cargo(CargoId("2"), LocationId("B"))
        ))
        factory.pickUpNextCargo()
        factory.cargoDelivered(factory.pickUpNextCargo().id)
        assertThat(factory.hasAllCargoDelivered(), equalTo(false))
    }

    @Test fun `can not be notified about delivery of a cargo that was not picked up`() {
        factory.produce(listOf(
            Cargo(CargoId("1"), LocationId("A"))
        ))
        factory.pickUpNextCargo()
        assertThrows<CargoNotPickedUp> {
            factory.cargoDelivered(CargoId("2"))
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

    @Test fun `cargo picked up are not delivered yet`() {
        factory.produce(listOf(
            Cargo(CargoId("1"), LocationId("A"))
        ))
        factory.pickUpNextCargo()
        assertThat(factory.hasAllCargoDelivered(), equalTo(false))
    }

    @Test fun `loads cargo on transport when arrives`() {
        factory.produce(listOf(
            Cargo(CargoId("1"), LocationId("A"))
        ))
        val transport = FakeTransport()
        factory.transportArrived(transport)
        assertThat(transport.cargoPickedUp, equalTo(CargoId("1")))
    }

    @Test fun `does not load cargo if none waiting`() {
        val transport = FakeTransport()
        factory.transportArrived(transport)
        assertThat(transport.cargoPickedUp, absent())
    }
}
