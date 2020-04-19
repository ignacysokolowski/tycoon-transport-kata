package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.Location
import tycoon.transport.domain.LocationUnknown
import tycoon.transport.domain.StationMap
import tycoon.transport.domain.Transport
import tycoon.transport.domain.TransportArrivalNotifier
import tycoon.transport.domain.cargo.Cargo
import tycoon.transport.domain.cargo.CargoId
import tycoon.transport.domain.delivery.Station

class StationMapStub(private val stations: Map<Location, Station>) : StationMap {
    override fun stationAt(location: Location): Station {
        return stations[location] ?: throw LocationUnknown()
    }
}

class StationSpy(override val location: Location) : Station {
    val arrivals = mutableListOf<Transport>()

    override fun transportArrived(transport: Transport) {
        arrivals.add(transport)
    }
}

class DummyTransport : Transport {
    override fun load(cargo: Cargo) {}
    override fun unload() = CargoId("dummy")
}

class TransportArrivalNotifierTest {

    @Test fun `notifies stations about transport arrival`() {
        val station = StationSpy(Location("A"))
        val notifier = TransportArrivalNotifier(StationMapStub(mapOf(station.location to station)))
        val transport = DummyTransport()

        notifier.transportArrived(transport, Location("A"))

        assertThat(station.arrivals, equalTo(listOf<Transport>(transport)))
    }
}
