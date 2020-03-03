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
        if (trip.journeyComplete()) {
            listener.truckArrived(this, trip.destination())
        }
    }

    override fun load(cargoId: CargoId) {
        this.cargoId = cargoId
    }

    override fun startTrip(trip: Trip) {
        this.trip = trip
    }

    fun drive(distance: Distance) {
        if (trip.journeyComplete()) {
            return
        }
        trip = trip.advancedBy(distance)
        notifyIfArrived()
    }

    override fun unload(): CargoId {
        val cargoId = cargoId
            ?: throw NoCargoCarried()
        this.cargoId = null
        return cargoId
    }

    override fun goBack() {
        trip = trip.backToOrigin()
    }
}
