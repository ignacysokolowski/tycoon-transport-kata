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
        app.addDistanceTo(LocationId("B"), Distance(5))
        assertThat(app.timeToDeliverCargoesToWarehouses(listOf("B")), equalTo(5))
    }

    @Test fun `truck has to travel back to the factory to pick up the next cargo`() {
        app.setTrucks(1)
        app.addDistanceTo(LocationId("B"), Distance(5))
        assertThat(app.timeToDeliverCargoesToWarehouses(listOf("B", "B")), equalTo(15))
    }

    @Test fun `two trucks deliver cargo in parallel`() {
        app.setTrucks(2)
        app.addDistanceTo(LocationId("B"), Distance(5))
        assertThat(app.timeToDeliverCargoesToWarehouses(listOf("B", "B")), equalTo(5))
    }

    @Test fun `one truck travels back and delivers the third cargo in 5 hours`() {
        app.setTrucks(2)
        app.addDistanceTo(LocationId("B"), Distance(5))
        assertThat(app.timeToDeliverCargoesToWarehouses(listOf("B", "B", "B")), equalTo(15))
    }

    @Test fun `after a short trip, a truck can pick up a cargo to a farther warehouse`() {
        app.setTrucks(2)
        app.addDistanceTo(LocationId("A"), Distance(1))
        app.addDistanceTo(LocationId("B"), Distance(5))
        assertThat(app.timeToDeliverCargoesToWarehouses(listOf("A", "B", "B")), equalTo(7))
    }

    @Test fun `total delivery time is zero after shipping no cargo`() {
        app.setTrucks(1)
        assertThat(app.timeToDeliverCargoesToWarehouses(listOf()), equalTo(0))
    }

    @Test fun `can not ship without trucks`() {
        app.setTrucks(0)
        val exception = assertThrows<IllegalStateException> {
            app.timeToDeliverCargoesToWarehouses(listOf("B"))
        }
        assertThat(exception.message, equalTo("No trucks at the factory"))
    }

    @Test fun `can not ship to unknown destinations`() {
        app.setTrucks(1)
        app.addDistanceTo(LocationId("B"), Distance(5))
        val exception = assertThrows<IllegalArgumentException> {
            app.timeToDeliverCargoesToWarehouses(listOf("X"))
        }
        assertThat(exception.message, equalTo("Unknown destination"))
    }
}
