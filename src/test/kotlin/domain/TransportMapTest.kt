package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.domain.Distance
import tycoon.transport.domain.Factory
import tycoon.transport.domain.Location
import tycoon.transport.domain.LocationId
import tycoon.transport.domain.LocationUnknown
import tycoon.transport.domain.Transport
import tycoon.transport.domain.TransportMap

class LocationStub : Location {
    override fun transportArrived(transport: Transport) {}
}

class TransportMapTest {

    private val factory = Factory()
    private val map = TransportMap(factory)

    @Test fun `contains locations`() {
        val location: Location = LocationStub()
        map.addLocation(LocationId("A"), location, Distance(5))
        assertThat(map.locationAt(LocationId("A")), equalTo(location))
    }

    @Test fun `provides the factory by its location id`() {
        assertThat(map.locationAt(factory.locationId), equalTo(factory as Location))
    }

    @Test fun `does not provide unknown locations`() {
        assertThrows<LocationUnknown> {
            map.locationAt(LocationId("A"))
        }
    }

    @Test fun `tells the distance to a location`() {
        map.addLocation(LocationId("A"), LocationStub(), Distance(5))
        assertThat(map.distanceTo(LocationId("A")), equalTo(Distance(5)))
    }

    @Test fun `can not tell the distance to unknown locations`() {
        assertThrows<LocationUnknown> {
            map.distanceTo(LocationId("A"))
        }
    }
}
