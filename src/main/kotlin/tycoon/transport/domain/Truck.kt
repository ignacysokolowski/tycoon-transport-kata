package tycoon.transport.domain

class Truck private constructor(
    private var trip: Trip,
    private val listener: TruckListener
) {

    companion object {
        fun on(trip: Trip, listener: TruckListener) = Truck(trip, listener)
    }

    private var distanceDriven = Distance(0)
    private val shipmentId = ShipmentId("1")

    init {
        notifyIfArrived()
    }

    fun pickUp(shipmentId: ShipmentId) {
    }

    fun startTrip(trip: Trip) {
        this.trip = trip
    }

    fun drive(distance: Distance) {
        if (trip.atDestination()) {
            throw TruckAtDestination()
        }
        trip = trip.advancedBy(distance)
        notifyIfArrived()
        distanceDriven += distance
    }

    private fun notifyIfArrived() {
        if (trip.atDestination()) {
            listener.truckArrived(this, trip.destination)
        }
    }

    fun dropOff(): ShipmentId {
        return shipmentId
    }

    fun atDestination() = trip.atDestination()

    fun distanceDriven() = distanceDriven
}
