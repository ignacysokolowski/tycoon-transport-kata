package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.Distance
import tycoon.transport.domain.Journey
import tycoon.transport.domain.LocationId
import tycoon.transport.domain.Trip

class TripTest {

    @Test fun `starts with a journey to the destination having full distance`() {
        val trip = Trip.between(LocationId("A"), LocationId("B"), Distance(3))
        assertThat(trip.journey(), equalTo(Journey.to(LocationId("B"), Distance(3))))
    }

    @Test fun `shortens the journey when advanced`() {
        val trip = Trip.between(LocationId("A"), LocationId("B"), Distance(3)).advancedBy(Distance(1))
        assertThat(trip.journey(), equalTo(Journey.to(LocationId("B"), Distance(2))))
    }

    @Test fun `changes direction of the journey when reversed`() {
        val trip = Trip.between(LocationId("A"), LocationId("B"), Distance(3))
        assertThat(trip.reversed().journey(), equalTo(Journey.to(LocationId("A"), Distance(3))))
        assertThat(trip.reversed().reversed().journey(), equalTo(Journey.to(LocationId("B"), Distance(3))))
    }

    @Test fun `resets the journey progress when reversed`() {
        val trip = Trip.between(LocationId("A"), LocationId("B"), Distance(3)).advancedBy(Distance(1)).reversed()
        assertThat(trip.journey(), equalTo(Journey.to(LocationId("A"), Distance(3))))
    }
}
