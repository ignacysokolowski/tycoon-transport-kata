package tycoon.transport.domain.carrier

import tycoon.transport.domain.Transport
import tycoon.transport.domain.cargo.Cargo
import tycoon.transport.domain.cargo.CargoId

class Truck private constructor(
    private var trip: Trip,
    private val tripPlanner: TripPlanner,
    private val listener: TransportListener
) : Transport {

    companion object {
        fun parked(tripPlanner: TripPlanner, listener: TransportListener) =
            Truck(tripPlanner.inPlaceTripAtOrigin(), tripPlanner, listener)
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
        trip = tripPlanner.tripTo(cargo.destination)
    }

    fun move() {
        if (trip.journeyComplete()) {
            return
        }
        trip = trip.advanced()
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
