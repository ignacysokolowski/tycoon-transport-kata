package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.domain.LegNotFound
import tycoon.transport.domain.Location
import tycoon.transport.domain.LocationUnknown
import tycoon.transport.domain.Transport
import tycoon.transport.domain.TransportMap
import tycoon.transport.domain.carrier.Distance
import tycoon.transport.domain.carrier.Leg
import tycoon.transport.domain.delivery.Station

class StationStub(override val location: Location) : Station {
    override fun transportArrived(transport: Transport) {}
}

class TransportMapTest {

    private val factory = StationStub(Location("FACTORY"))
    private val map = TransportMap(factory)

    @Test fun `contains stations`() {
        val station: Station = StationStub(Location("A"))
        map.addStation(station, Distance(5))
        assertThat(map.stationAt(Location("A")), equalTo(station))
    }

    @Test fun `provides the factory by its location`() {
        assertThat(map.stationAt(factory.location), equalTo(factory as Station))
    }

    @Test fun `does not provide unknown stations`() {
        assertThrows<LocationUnknown> {
            map.stationAt(Location("A"))
        }
    }

    @Test fun `tells the distance to a location`() {
        map.addStation(StationStub(Location("A")), Distance(5))
        assertThat(map.distanceTo(Location("A")), equalTo(Distance(5)))
    }

    @Test fun `can not tell the distance to unknown locations`() {
        assertThrows<LocationUnknown> {
            map.distanceTo(Location("A"))
        }
    }

    @Test fun `provides legs between the factory and a destination`() {
        map.addStation(StationStub(Location("A")), Distance(5))
        assertThat(
            map.legBetween(Location("FACTORY"), Location("A")),
            equalTo(Leg(Location("FACTORY"), Location("A"), Distance(5)))
        )
    }

    @Test fun `only provides legs to existing locations`() {
        assertThrows<LegNotFound> {
            map.legBetween(Location("FACTORY"), Location("A"))
        }
    }

    @Test fun `provides legs only from the factory`() {
        map.addStation(StationStub(Location("A")), Distance(5))
        assertThrows<LegNotFound> {
            map.legBetween(Location("X"), Location("A"))
        }
    }
}
