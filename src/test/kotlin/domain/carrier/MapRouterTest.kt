package domain.carrier

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.Location
import tycoon.transport.domain.LocationUnknown
import tycoon.transport.domain.carrier.Distance
import tycoon.transport.domain.carrier.DistanceMap
import tycoon.transport.domain.carrier.MapRouter
import tycoon.transport.domain.carrier.Trip

class MapRouterTest {

    private val router = MapRouter(Location("ORIGIN"), DistanceMapStub())

    @Test fun `creates an in-place trip at the origin`() {
        assertThat(
            router.inPlaceTripAtOrigin(),
            equalTo(Trip.inPlace(Location("ORIGIN")))
        )
    }

    @Test fun `creates trips from the origin to a destination`() {
        assertThat(
            router.tripTo(Location("D")),
            equalTo(Trip.between(Location("ORIGIN"), Location("D"), Distance(4)))
        )
    }
}

class DistanceMapStub : DistanceMap {
    override fun distanceTo(location: Location): Distance {
        if (location != Location("D")) {
            throw LocationUnknown()
        }
        return Distance(4)
    }
}
