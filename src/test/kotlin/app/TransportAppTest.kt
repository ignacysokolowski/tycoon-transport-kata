package app

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.app.TransportApp
import tycoon.transport.domain.Distance
import tycoon.transport.domain.DistanceMap
import tycoon.transport.domain.LocationId

class TransportAppTest {

    private val map = DistanceMap()
    private val app = TransportApp(map)

    @Test fun `ships cargo to a warehouse`() {
        app.setTrucks(1)
        map.addDistanceTo(LocationId("B"), Distance(5))
        app.ship(listOf("B"))
        assertThat(app.totalDeliveryTime(), equalTo(5))
    }

    @Test fun `truck has to travel back to the factory to pick up the next shipment`() {
        app.setTrucks(1)
        map.addDistanceTo(LocationId("B"), Distance(5))
        app.ship(listOf("B", "B"))
        assertThat(app.totalDeliveryTime(), equalTo(15))
    }

    @Test fun `two trucks deliver cargo in parallel`() {
        app.setTrucks(2)
        map.addDistanceTo(LocationId("B"), Distance(5))
        app.ship(listOf("B", "B"))
        assertThat(app.totalDeliveryTime(), equalTo(5))
    }

    @Test fun `one truck travels back and delivers the third cargo in 5 hours`() {
        app.setTrucks(2)
        map.addDistanceTo(LocationId("B"), Distance(5))
        app.ship(listOf("B", "B", "B"))
        assertThat(app.totalDeliveryTime(), equalTo(15))
    }

    @Test fun `after a short trip, a truck can pick up a cargo to a farther warehouse`() {
        app.setTrucks(2)
        map.addDistanceTo(LocationId("A"), Distance(1))
        map.addDistanceTo(LocationId("B"), Distance(5))
        app.ship(listOf("A", "B", "B"))
        assertThat(app.totalDeliveryTime(), equalTo(7))
    }

    @Test fun `total delivery time is zero before anything has been shipped`() {
        app.setTrucks(1)
        assertThat(app.totalDeliveryTime(), equalTo(0))
    }

    @Test fun `total delivery time is zero after shipping no cargo`() {
        app.setTrucks(1)
        app.ship(emptyList())
        assertThat(app.totalDeliveryTime(), equalTo(0))
    }

    @Test fun `can not ship without trucks`() {
        app.setTrucks(0)
        val exception = assertThrows<IllegalStateException> {
            app.ship(listOf("B"))
        }
        assertThat(exception.message, equalTo("No trucks at the factory"))
    }

    @Test fun `can not ship to unknown destinations`() {
        app.setTrucks(1)
        map.addDistanceTo(LocationId("B"), Distance(5))
        val exception = assertThrows<IllegalArgumentException> {
            app.ship(listOf("X"))
        }
        assertThat(exception.message, equalTo("Unknown destination"))
    }
}
