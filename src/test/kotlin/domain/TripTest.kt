package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.Distance
import tycoon.transport.domain.Trip

class TripTest {

    @Test fun `has distance`() {
        val trip = Trip(Distance(3))
        assertThat(trip.distance, equalTo(Distance(3)))
    }
}
