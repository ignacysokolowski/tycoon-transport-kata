package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.LocationId
import tycoon.transport.domain.LocationUnknown
import tycoon.transport.domain.StationMap
import tycoon.transport.domain.Transport
import tycoon.transport.domain.TransportArrivalNotifier
import tycoon.transport.domain.cargo.Cargo
import tycoon.transport.domain.cargo.CargoId
import tycoon.transport.domain.delivery.Station

class StationMapStub(private val stations: Map<LocationId, Station>) : StationMap {
    override fun stationAt(locationId: LocationId): Station {
        return stations[locationId] ?: throw LocationUnknown()
    }
}

class StationSpy(override val locationId: LocationId) : Station {
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
        val station = StationSpy(LocationId("A"))
        val notifier = TransportArrivalNotifier(StationMapStub(mapOf(station.locationId to station)))
        val transport = DummyTransport()

        notifier.transportArrived(transport, LocationId("A"))

        assertThat(station.arrivals, equalTo(listOf<Transport>(transport)))
    }
}
