package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.Distance
import tycoon.transport.domain.Journey
import tycoon.transport.domain.LocationId

class JourneyTest {

    @Test fun `is at destination if has no distance`() {
        assertThat(Journey.to(LocationId("A"), Distance(0)).atDestination(), equalTo(true))
    }

    @Test fun `is not at destination if has a distance`() {
        assertThat(Journey.to(LocationId("A"), Distance(1)).atDestination(), equalTo(false))
    }

    @Test fun `gets a shorter distance when advanced`() {
        assertThat(
            Journey.to(LocationId("A"), Distance(3)).advancedBy(Distance(1)),
            equalTo(Journey.to(LocationId("A"), Distance(2)))
        )
    }
}
