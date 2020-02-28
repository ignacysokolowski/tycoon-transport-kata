package tycoon.transport.domain

class Truck {
    var trip = Trip(Distance(0))

    private var distanceDriven = Distance(0)

    fun startTrip(trip: Trip) {
        this.trip = trip
    }

    fun drive(distance: Distance) {
        distanceDriven += distance
    }

    fun distanceDriven() = distanceDriven
}
