package tycoon.transport.domain

class Truck private constructor(var trip: Trip) {

    companion object {
        fun on(trip: Trip) = Truck(trip)
    }

    private var distanceDriven = Distance(0)

    fun startTrip(trip: Trip) {
        this.trip = trip
    }

    fun drive(distance: Distance) {
        if (trip.atDestination()) {
            throw TruckAtDestination()
        }
        trip = trip.advancedBy(distance)
        distanceDriven += distance
    }

    fun atDestination() = trip.atDestination()

    fun distanceDriven() = distanceDriven
}
