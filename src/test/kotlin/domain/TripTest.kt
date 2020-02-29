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

    @Test fun `changes direction of the journey when turned back to origin`() {
        val trip = Trip.between(LocationId("A"), LocationId("B"), Distance(3))
        assertThat(trip.backToOrigin().journey(), equalTo(Journey.to(LocationId("A"), Distance(3))))
        assertThat(trip.backToOrigin().backToOrigin().journey(), equalTo(Journey.to(LocationId("B"), Distance(3))))
    }

    @Test fun `resets the journey progress when turned back to origin`() {
        val trip = Trip.between(LocationId("A"), LocationId("B"), Distance(3)).advancedBy(Distance(1)).backToOrigin()
        assertThat(trip.journey(), equalTo(Journey.to(LocationId("A"), Distance(3))))
    }

    @Test fun `is not at destination until completes the journey`() {
        val trip = Trip.between(LocationId("A"), LocationId("B"), Distance(3))
        assertThat(trip.atDestination(), equalTo(false))
        assertThat(trip.advancedBy(Distance(1)).atDestination(), equalTo(false))
    }

    @Test fun `can be advanced to the destination`() {
        val trip = Trip.between(LocationId("A"), LocationId("B"), Distance(2))
        assertThat(trip.advancedBy(Distance(1)).advancedBy(Distance(1)).atDestination(), equalTo(true))
    }

    @Test fun `trip in place has a zero-distance journey`() {
        val trip = Trip.inPlace(LocationId("A"))
        assertThat(trip.journey(), equalTo(Journey.to(LocationId("A"), Distance(0))))
    }

    @Test fun `trip in place has then same origin and destination`() {
        val trip = Trip.inPlace(LocationId("A"))
        assertThat(trip.backToOrigin(), equalTo(trip))
    }

    @Test fun `trip in place can not be advanced`() {
        val trip = Trip.inPlace(LocationId("A"))
        assertThat(trip.advancedBy(Distance(1)), equalTo(trip))
    }
}
