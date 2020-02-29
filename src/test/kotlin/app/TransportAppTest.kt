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
        map.addWarehouse(LocationId("B"), Distance(5))
        app.ship(listOf("B"))
        assertThat(app.totalDeliveryTime(), equalTo(5))
    }

    @Test fun `truck has to travel back to the factory to pick up the next shipment`() {
        map.addWarehouse(LocationId("B"), Distance(5))
        app.ship(listOf("B", "B"))
        assertThat(app.totalDeliveryTime(), equalTo(15))
    }

    @Test fun `total delivery time is zero before anything has been shipped`() {
        assertThat(app.totalDeliveryTime(), equalTo(0))
    }

    @Test fun `can not ship to unknown destinations`() {
        map.addWarehouse(LocationId("B"), Distance(5))
        val exception = assertThrows<RuntimeException> {
            app.ship(listOf("X"))
        }
        assertThat(exception.message, equalTo("Unknown destination"))
    }
}
