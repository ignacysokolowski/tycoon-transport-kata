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
import tycoon.transport.domain.carrier.routing.Leg
import tycoon.transport.domain.delivery.Station

class StationStub(override val location: Location) : Station {
    override fun transportArrived(transport: Transport) {}
}

class TransportMapTest {

    private val factory = StationStub(Location("FACTORY"))
    private val map = TransportMap(factory)

    @Test fun `localizes stations by their locations`() {
        val station: Station = StationStub(Location("A"))
        map.addStationBehind(factory.location, station, Distance(5))
        assertThat(map.stationAt(Location("A")), equalTo(station))
    }

    @Test fun `localizes the factory by its location`() {
        assertThat(map.stationAt(factory.location), equalTo(factory as Station))
    }

    @Test fun `localizes stations behind other stations`() {
        map.addStationBehind(factory.location, StationStub(Location("A")), Distance(5))
        val station: Station = StationStub(Location("B"))
        map.addStationBehind(Location("A"), station, Distance(2))
        assertThat(map.stationAt(Location("B")), equalTo(station))
    }

    @Test fun `does not localize unknown stations`() {
        assertThrows<LocationUnknown> {
            map.stationAt(Location("A"))
        }
    }

    @Test fun `does not allow adding stations behind non-existent stations`() {
        assertThrows<LocationUnknown> {
            map.addStationBehind(Location("A"), StationStub(Location("B")), Distance(2))
        }
    }

    @Test fun `does not allow adding two stations at the same location`() {
        map.addStationBehind(factory.location, StationStub(Location("A")), Distance(5))
        val error = assertThrows<IllegalArgumentException> {
            map.addStationBehind(factory.location, StationStub(Location("A")), Distance(5))
        }
        assertThat(error.message, equalTo("Can not add two stations at the same location"))
    }

    @Test fun `does not allow adding stations behind stations not directly connected to the factory`() {
        map.addStationBehind(factory.location, StationStub(Location("A")), Distance(2))
        map.addStationBehind(Location("A"), StationStub(Location("B")), Distance(2))
        val error = assertThrows<IllegalArgumentException> {
            map.addStationBehind(Location("B"), StationStub(Location("C")), Distance(2))
        }
        assertThat(
            error.message, equalTo(
                "Can not add stations behind ${Location("B")} as " +
                    "it's not directly connected to the factory"
            )
        )
    }

    @Test fun `routes from the factory to a station`() {
        map.addStationBehind(factory.location, StationStub(Location("A")), Distance(5))
        assertThat(
            map.firstLegBetween(Location("FACTORY"), Location("A")),
            equalTo(Leg(Location("FACTORY"), Location("A"), Distance(5)))
        )
    }

    @Test fun `only routes to existing stations`() {
        assertThrows<LegNotFound> {
            map.firstLegBetween(Location("FACTORY"), Location("A"))
        }
    }

    @Test fun `only routes from existing stations`() {
        map.addStationBehind(factory.location, StationStub(Location("A")), Distance(5))
        assertThrows<LegNotFound> {
            map.firstLegBetween(Location("X"), Location("A"))
        }
    }

    @Test fun `routes to the first intermediate station between origin and destination`() {
        map.addStationBehind(Location("FACTORY"), StationStub(Location("A")), Distance(5))
        map.addStationBehind(Location("A"), StationStub(Location("B")), Distance(2))
        assertThat(
            map.firstLegBetween(Location("FACTORY"), Location("B")),
            equalTo(Leg(Location("FACTORY"), Location("A"), Distance(5)))
        )
    }

    @Test fun `routes from intermediate stations`() {
        map.addStationBehind(factory.location, StationStub(Location("A")), Distance(5))
        map.addStationBehind(Location("A"), StationStub(Location("B")), Distance(2))
        assertThat(
            map.firstLegBetween(Location("A"), Location("B")),
            equalTo(Leg(Location("A"), Location("B"), Distance(2)))
        )
    }

    @Test fun `does not route between stations that are not connected`() {
        map.addStationBehind(factory.location, StationStub(Location("A")), Distance(5))
        map.addStationBehind(factory.location, StationStub(Location("B")), Distance(5))
        assertThrows<LegNotFound> {
            map.firstLegBetween(Location("A"), Location("B"))
        }
    }
}
