package tycoon.transport.domain

class Truck private constructor(
    private var trip: Trip,
    private val router: Router,
    private val listener: TransportListener
) : Transport {

    companion object {
        fun parked(router: Router, listener: TransportListener) =
            Truck(router.inPlaceTripAtOrigin(), router, listener)
    }

    private var carriedCargoId: CargoId? = null

    init {
        notifyIfArrived()
    }

    private fun notifyIfArrived() {
        if (trip.journeyComplete()) {
            listener.transportArrived(this, trip.journeyDestination())
        }
    }

    override fun load(cargo: Cargo) {
        carriedCargoId = cargo.id
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
        val unloadedCargoId = this.carriedCargoId
            ?: throw NoCargoCarried()
        this.carriedCargoId = null
        trip = trip.backToOrigin()
        return unloadedCargoId
    }
}
