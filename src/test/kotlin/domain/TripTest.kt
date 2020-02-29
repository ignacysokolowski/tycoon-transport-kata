package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.Distance
import tycoon.transport.domain.LocationId
import tycoon.transport.domain.Trip

class TripTest {

    @Test fun `is at destination if has no distance`() {
        assertThat(Trip.to(LocationId("A"), Distance(0)).atDestination(), equalTo(true))
    }

    @Test fun `is not at destination if has a distance`() {
        assertThat(Trip(LocationId("A"), Distance(1)).atDestination(), equalTo(false))
    }

    @Test fun `gets a shorter distance when advanced`() {
        assertThat(
            Trip(LocationId("A"), Distance(3)).advancedBy(Distance(1)),
            equalTo(Trip(LocationId("A"), Distance(2)))
        )
    }
}
