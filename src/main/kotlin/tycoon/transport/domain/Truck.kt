package tycoon.transport.domain

class Truck private constructor(
    private var trip: Trip,
    private val listener: TruckListener
) : Transport {

    companion object {
        fun parked(router: Router, listener: TruckListener) =
            Truck(router.inPlaceTripAtOrigin(), listener)
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

    override fun load(cargo: Cargo) {
        this.cargoId = cargo.id
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
