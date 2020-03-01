package tycoon.transport.domain

class Truck private constructor(
    private var trip: Trip,
    private val listener: TruckListener
) : Transport {

    companion object {
        fun on(trip: Trip, listener: TruckListener) = Truck(trip, listener)
    }

    private var cargoId: CargoId? = null

    init {
        notifyIfArrived()
    }

    private fun notifyIfArrived() {
        if (trip.atDestination()) {
            listener.truckArrived(this, trip.destination())
        }
    }

    fun pickUp(cargoId: CargoId) {
        this.cargoId = cargoId
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

    override fun dropOff(): CargoId {
        val cargoId = cargoId
            ?: throw NoCargoCarried()
        this.cargoId = null
        return cargoId
    }

    override fun goBack() {
        trip = trip.backToOrigin()
    }
}
