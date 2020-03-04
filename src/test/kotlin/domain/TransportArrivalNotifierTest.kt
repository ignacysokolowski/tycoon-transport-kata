package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.Location
import tycoon.transport.domain.LocationId
import tycoon.transport.domain.LocationMap
import tycoon.transport.domain.LocationUnknown
import tycoon.transport.domain.Transport
import tycoon.transport.domain.TransportArrivalNotifier

class LocationMapStub(private val locations: Map<LocationId, Location>) : LocationMap {
    override fun locationAt(locationId: LocationId): Location {
        return locations[locationId] ?: throw LocationUnknown()
    }
}

class LocationSpy(override val locationId: LocationId) : Location {
    val arrivals = mutableListOf<Transport>()

    override fun transportArrived(transport: Transport) {
        arrivals.add(transport)
    }
}

class TransportArrivalNotifierTest {

    @Test fun `notifies location about transport arrival`() {
        val location = LocationSpy(LocationId("A"))
        val notifier = TransportArrivalNotifier(LocationMapStub(mapOf(location.locationId to location)))
        val transport = FakeTransport()

        notifier.transportArrived(transport, LocationId("A"))

        assertThat(location.arrivals, equalTo(listOf<Transport>(transport)))
    }
}
