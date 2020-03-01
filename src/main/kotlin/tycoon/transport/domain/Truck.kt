package tycoon.transport.domain

class Truck private constructor(
    private var trip: Trip,
    private val listener: TruckListener
) : Transport {

    companion object {
        fun on(trip: Trip, listener: TruckListener) = Truck(trip, listener)
    }

    private var shipmentId: ShipmentId? = null

    init {
        notifyIfArrived()
    }

    private fun notifyIfArrived() {
        if (trip.atDestination()) {
            listener.truckArrived(this, trip.destination())
        }
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
    }

    override fun dropOff(): ShipmentId {
        val shipmentId = shipmentId
            ?: throw NoCargoCarried()
        this.shipmentId = null
        return shipmentId
    }

    override fun goBack() {
        trip = trip.backToOrigin()
    }
}
