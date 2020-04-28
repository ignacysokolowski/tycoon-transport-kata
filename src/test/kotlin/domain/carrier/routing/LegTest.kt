package domain.carrier.routing

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.Location
import tycoon.transport.domain.carrier.Distance
import tycoon.transport.domain.carrier.Trip
import tycoon.transport.domain.carrier.routing.Leg

class LegTest {

    @Test
    fun `creates a trip along the leg`() {
        assertThat(
            Leg(Location("A"), Location("B"), Distance(3)).newTrip(),
            equalTo(Trip.between(Location("A"), Location("B"), Distance(3)))
        )
    }
}
