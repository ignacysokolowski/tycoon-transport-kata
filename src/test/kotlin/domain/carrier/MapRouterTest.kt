package domain.carrier

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.LocationId
import tycoon.transport.domain.LocationUnknown
import tycoon.transport.domain.carrier.Distance
import tycoon.transport.domain.carrier.DistanceMap
import tycoon.transport.domain.carrier.MapRouter
import tycoon.transport.domain.carrier.Trip

class MapRouterTest {

    private val router = MapRouter(LocationId("ORIGIN"), DistanceMapStub())

    @Test fun `creates an in-place trip at the origin`() {
        assertThat(
            router.inPlaceTripAtOrigin(),
            equalTo(Trip.inPlace(LocationId("ORIGIN")))
        )
    }

    @Test fun `creates trips from the origin to a destination`() {
        assertThat(
            router.tripTo(LocationId("D")),
            equalTo(Trip.between(LocationId("ORIGIN"), LocationId("D"), Distance(4)))
        )
    }
}

class DistanceMapStub : DistanceMap {
    override fun distanceTo(location: LocationId): Distance {
        if (location != LocationId("D")) {
            throw LocationUnknown()
        }
        return Distance(4)
    }
}
