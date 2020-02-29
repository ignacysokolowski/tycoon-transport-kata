package tycoon.transport.domain

class Truck private constructor(
    private var trip: Trip,
    private val listener: TruckListener
) {

    companion object {
        fun on(trip: Trip, listener: TruckListener) = Truck(trip, listener)
    }

    private var distanceDriven = Distance(0)
    private var shipmentId: ShipmentId? = null

    init {
        notifyIfArrived()
    }

    fun pickUp(shipmentId: ShipmentId) {
        this.shipmentId = shipmentId
    }

    fun startTrip(trip: Trip) {
        this.trip = trip
    }

    fun drive(distance: Distance) {
        if (trip.atDestination()) {
            return
        }
        trip = trip.advancedBy(distance)
        notifyIfArrived()
        distanceDriven += distance
    }

    private fun notifyIfArrived() {
        if (trip.atDestination()) {
            listener.truckArrived(this, trip.destination())
        }
    }

    fun dropOff(): ShipmentId {
        val shipmentId = shipmentId
            ?: throw NoShipmentCarried()
        this.shipmentId = null
        return shipmentId
    }

    fun distanceDriven() = distanceDriven
}
