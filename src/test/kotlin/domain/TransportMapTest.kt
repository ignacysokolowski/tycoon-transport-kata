package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.domain.LocationId
import tycoon.transport.domain.LocationUnknown
import tycoon.transport.domain.Transport
import tycoon.transport.domain.TransportMap
import tycoon.transport.domain.carrier.Distance
import tycoon.transport.domain.delivery.Station

class StationStub(override val locationId: LocationId) : Station {
    override fun transportArrived(transport: Transport) {}
}

class TransportMapTest {

    private val factory = StationStub(LocationId("FACTORY"))
    private val map = TransportMap(factory)

    @Test fun `contains stations`() {
        val station: Station = StationStub(LocationId("A"))
        map.addStation(station, Distance(5))
        assertThat(map.stationAt(LocationId("A")), equalTo(station))
    }

    @Test fun `provides the factory by its location id`() {
        assertThat(map.stationAt(factory.locationId), equalTo(factory as Station))
    }

    @Test fun `does not provide unknown stations`() {
        assertThrows<LocationUnknown> {
            map.stationAt(LocationId("A"))
        }
    }

    @Test fun `tells the distance to a location`() {
        map.addStation(StationStub(LocationId("A")), Distance(5))
        assertThat(map.distanceTo(LocationId("A")), equalTo(Distance(5)))
    }

    @Test fun `can not tell the distance to unknown locations`() {
        assertThrows<LocationUnknown> {
            map.distanceTo(LocationId("A"))
        }
    }
}
