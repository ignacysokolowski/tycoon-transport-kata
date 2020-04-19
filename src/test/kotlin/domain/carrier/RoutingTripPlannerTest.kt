package domain.carrier

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.Location
import tycoon.transport.domain.carrier.RoutingTripPlanner
import tycoon.transport.domain.carrier.Trip

class RoutingTripPlannerTest {

    private val tripPlanner = RoutingTripPlanner()

    @Test fun `creates an in-place trip at the origin`() {
        assertThat(
            tripPlanner.inPlaceTripAtOrigin(),
            equalTo(Trip.inPlace(Location("ORIGIN")))
        )
    }
}
