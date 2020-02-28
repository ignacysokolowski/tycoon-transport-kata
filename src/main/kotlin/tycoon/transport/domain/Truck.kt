package tycoon.transport.domain

class Truck(var trip: Trip) {
    private var distanceDriven = Distance(0)

    fun startTrip(trip: Trip) {
        this.trip = trip
    }

    fun drive(distance: Distance) {
        if (trip.atDestination()) {
            throw TruckAtDestination()
        }
        distanceDriven += distance
    }

    fun distanceDriven() = distanceDriven
}
