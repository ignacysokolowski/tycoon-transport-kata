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

    fun pickUp(shipmentId: ShipmentId) {
        this.shipmentId = shipmentId
    }

    fun startTrip(trip: Trip) {
        this.trip = trip
    }

    override fun goBack() {
        trip = trip.backToOrigin()
    }

    fun drive(distance: Distance) {
        if (trip.atDestination()) {
            return
        }
        trip = trip.advancedBy(distance)
        notifyIfArrived()
    }

    private fun notifyIfArrived() {
        if (trip.atDestination()) {
            listener.truckArrived(this, trip.destination())
        }
    }

    override fun dropOff(): ShipmentId {
        val shipmentId = shipmentId
            ?: throw NoShipmentCarried()
        this.shipmentId = null
        return shipmentId
    }
}
