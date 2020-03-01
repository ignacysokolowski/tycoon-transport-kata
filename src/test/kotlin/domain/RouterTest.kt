package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.Distance
import tycoon.transport.domain.DistanceMap
import tycoon.transport.domain.LocationId
import tycoon.transport.domain.Router
import tycoon.transport.domain.Trip

class RouterTest {

    private val map = DistanceMap()
    private val router = Router(LocationId("ORIGIN"), map)

    @Test fun `creates an in-place trip at the origin`() {
        assertThat(
            router.inPlaceTripAtOrigin(),
            equalTo(Trip.inPlace(LocationId("ORIGIN")))
        )
    }

    @Test fun `creates trips from the origin to a destination`() {
        map.addDistanceTo(LocationId("D"), Distance(4))
        assertThat(
            router.tripTo(LocationId("D")),
            equalTo(Trip.between(LocationId("ORIGIN"), LocationId("D"), Distance(4)))
        )
    }
}
