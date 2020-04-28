package domain.carrier.routing

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.Location
import tycoon.transport.domain.carrier.Distance
import tycoon.transport.domain.carrier.Trip
import tycoon.transport.domain.carrier.routing.Leg
import tycoon.transport.domain.carrier.routing.Router
import tycoon.transport.domain.carrier.routing.RoutingTripPlanner

class RoutingTripPlannerTest {

    private val router = RouterStub()
    private val tripPlanner = RoutingTripPlanner(Location("ORIGIN"), router)

    @Test fun `creates an in-place trip at the origin`() {
        assertThat(
            tripPlanner.inPlaceTripAtOrigin(),
            equalTo(Trip.inPlace(Location("ORIGIN")))
        )
    }

    @Test fun `creates trips on the first leg between the origin and destination`() {
        assertThat(
            tripPlanner.tripTo(Location("D")),
            equalTo(Trip.between(Location("ORIGIN"), Location("D"), Distance(4)))
        )
    }
}

class RouterStub : Router {
    override fun firstLegBetween(origin: Location, destination: Location) =
        Leg(origin, destination, Distance(4))
}
