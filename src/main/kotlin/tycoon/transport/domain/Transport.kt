package tycoon.transport.domain

interface Transport {
    fun unload(): CargoId
    fun goBack()
    fun startTrip(trip: Trip)
    fun load(cargo: Cargo)
}
