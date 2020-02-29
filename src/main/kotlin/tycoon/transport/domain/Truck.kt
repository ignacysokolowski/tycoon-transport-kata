package tycoon.transport.domain

class Truck private constructor(
    private var trip: Trip,
    private val listener: TruckListener
) {

    companion object {
        fun on(
            trip: Trip,
            listener: TruckListener = object : TruckListener {
                override fun truckArrived(truck: Truck, locationId: LocationId) {
                }
            }
        ) = Truck(trip, listener)
    }

    private var distanceDriven = Distance(0)

    fun startTrip(trip: Trip) {
        this.trip = trip
    }

    fun drive(distance: Distance) {
        listener.truckArrived(this, trip.destination)
        if (trip.atDestination()) {
            throw TruckAtDestination()
        }
        trip = trip.advancedBy(distance)
        distanceDriven += distance
    }

    fun atDestination() = trip.atDestination()

    fun distanceDriven() = distanceDriven
}
