package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.LocationId
import tycoon.transport.domain.Router
import tycoon.transport.domain.Trip

class RouterTest {

    private val originLocationId = LocationId("ORIGIN")
    private val router = Router(originLocationId)

    @Test fun `creates an in-place trip at the origin`() {
        assertThat(router.inPlaceTripAtOrigin(), equalTo(Trip.inPlace(originLocationId)))
    }
}
