package domain.carrier

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.Location
import tycoon.transport.domain.carrier.Distance
import tycoon.transport.domain.carrier.Journey
import tycoon.transport.domain.carrier.Leg
import tycoon.transport.domain.carrier.Trip

class TripTest {

    @Test fun `starts with a journey of full distance to the destination`() {
        val trip = Trip.between(Location("A"), Location("B"), Distance(3))
        assertThat(trip.journey(), equalTo(Journey.to(Location("B"), Distance(3))))
    }

    @Test fun `shortens the journey when advanced`() {
        val trip = Trip.between(Location("A"), Location("B"), Distance(3)).advancedBy(Distance(1))
        assertThat(trip.journey(), equalTo(Journey.to(Location("B"), Distance(2))))
    }

    @Test fun `changes direction of the journey when turned back to origin`() {
        val trip = Trip.between(Location("A"), Location("B"), Distance(3))
        assertThat(trip.backToOrigin().journey(), equalTo(Journey.to(Location("A"), Distance(3))))
    }

    @Test fun `does not turn back if already heading origin`() {
        val trip = Trip.between(Location("A"), Location("B"), Distance(3))
        assertThat(trip.backToOrigin().backToOrigin(), equalTo(trip.backToOrigin()))
    }

    @Test fun `resets the journey progress when turned back to origin`() {
        val trip = Trip.between(Location("A"), Location("B"), Distance(3)).advancedBy(Distance(1)).backToOrigin()
        assertThat(trip.journey(), equalTo(Journey.to(Location("A"), Distance(3))))
    }

    @Test fun `does not complete the journey before advanced to its distance`() {
        val trip = Trip.between(Location("A"), Location("B"), Distance(3))
        assertThat(trip.journeyComplete(), equalTo(false))
        assertThat(trip.advancedBy(Distance(1)).journeyComplete(), equalTo(false))
    }

    @Test fun `can be advanced to the destination to complete the journey`() {
        val trip = Trip.between(Location("A"), Location("B"), Distance(2))
        assertThat(trip.advancedBy(Distance(1)).advancedBy(Distance(1)).journeyComplete(), equalTo(true))
    }

    @Test fun `can be advanced to the origin after turning back`() {
        val trip = Trip.between(Location("A"), Location("B"), Distance(1))
        assertThat(trip.advancedBy(Distance(1)).backToOrigin().advancedBy(Distance(1)).journeyComplete(), equalTo(true))
    }

    @Test fun `trip in place has a zero-distance journey`() {
        val trip = Trip.inPlace(Location("A"))
        assertThat(trip.journey(), equalTo(Journey.to(Location("A"), Distance(0))))
    }

    @Test fun `trip in place has then same origin and destination`() {
        val trip = Trip.inPlace(Location("A"))
        assertThat(trip.backToOrigin(), equalTo(trip))
    }

    @Test fun `trip in place can not be advanced`() {
        val trip = Trip.inPlace(Location("A"))
        assertThat(trip.advancedBy(Distance(1)), equalTo(trip))
    }

    @Test fun `can be created from a leg`() {
        assertThat(
            Trip.from(Leg(Location("A"), Location("B"), Distance(3))),
            equalTo(Trip.between(Location("A"), Location("B"), Distance(3)))
        )
    }
}
