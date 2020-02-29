package tycoon.transport.domain

class Truck private constructor(
    private var journey: Journey,
    private val listener: TruckListener
) {

    companion object {
        fun on(trip: Trip, listener: TruckListener) = Truck(trip.journey(), listener)
    }

    private var distanceDriven = Distance(0)
    private var shipmentId: ShipmentId? = null

    init {
        notifyIfArrived()
    }

    fun pickUp(shipmentId: ShipmentId) {
        this.shipmentId = shipmentId
    }

    fun startJourney(journey: Journey) {
        this.journey = journey
    }

    fun drive(distance: Distance) {
        if (journey.atDestination()) {
            return
        }
        journey = journey.advancedBy(distance)
        notifyIfArrived()
        distanceDriven += distance
    }

    private fun notifyIfArrived() {
        if (journey.atDestination()) {
            listener.truckArrived(this, journey.destination)
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
