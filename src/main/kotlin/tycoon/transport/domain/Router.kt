package tycoon.transport.domain

interface Router {
    fun inPlaceTripAtOrigin(): Trip
    fun tripTo(destination: LocationId): Trip
}
