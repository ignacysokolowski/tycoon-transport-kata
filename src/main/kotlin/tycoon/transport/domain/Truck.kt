package tycoon.transport.domain

class Truck private constructor(
    private var trip: Trip,
    private val router: Router,
    private val listener: TruckListener
) : Transport {

    companion object {
        fun parked(router: Router, listener: TruckListener) =
            Truck(router.inPlaceTripAtOrigin(), router, listener)
    }

    private var cargoId: CargoId? = null

    init {
        notifyIfArrived()
    }

    private fun notifyIfArrived() {
        if (trip.journeyComplete()) {
            listener.transportArrived(this, trip.destination())
        }
    }

    override fun load(cargo: Cargo) {
        cargoId = cargo.id
        trip = router.tripTo(cargo.destination)
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
        trip = trip.backToOrigin()
        return cargoId
    }
}
