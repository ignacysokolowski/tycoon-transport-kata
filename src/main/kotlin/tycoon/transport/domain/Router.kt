package tycoon.transport.domain

class Router(private val origin: LocationId) {
    fun inPlaceTripAtOrigin() = Trip.inPlace(origin)
}
