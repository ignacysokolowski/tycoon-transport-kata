package domain.carrier

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.Location
import tycoon.transport.domain.carrier.Distance
import tycoon.transport.domain.carrier.Journey

class JourneyTest {

    @Test fun `is at destination if has no distance`() {
        assertThat(Journey.to(Location("A"), Distance(0)).atDestination(), equalTo(true))
    }

    @Test fun `is not at destination if has a distance`() {
        assertThat(Journey.to(Location("A"), Distance(1)).atDestination(), equalTo(false))
    }

    @Test fun `gets a shorter distance when advanced`() {
        assertThat(
            Journey.to(Location("A"), Distance(3)).advanced(),
            equalTo(Journey.to(Location("A"), Distance(2)))
        )
    }

    @Test fun `can not be advanced when already at destination`() {
        assertThat(
            Journey.to(Location("A"), Distance(0)).advanced(),
            equalTo(Journey.to(Location("A"), Distance(0)))
        )
    }
}
