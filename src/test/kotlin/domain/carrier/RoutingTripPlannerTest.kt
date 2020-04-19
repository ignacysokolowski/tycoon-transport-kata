package domain.carrier

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.Location
import tycoon.transport.domain.carrier.Distance
import tycoon.transport.domain.carrier.RoutingTripPlanner
import tycoon.transport.domain.carrier.Trip

class RoutingTripPlannerTest {

    private val tripPlanner = RoutingTripPlanner(Location("ORIGIN"))

    @Test fun `creates an in-place trip at the origin`() {
        assertThat(
            tripPlanner.inPlaceTripAtOrigin(),
            equalTo(Trip.inPlace(Location("ORIGIN")))
        )
    }

    @Test fun `creates trips from the origin to a destination`() {
        assertThat(
            tripPlanner.tripTo(Location("D")),
            equalTo(Trip.between(Location("ORIGIN"), Location("D"), Distance(4)))
        )
    }
}
