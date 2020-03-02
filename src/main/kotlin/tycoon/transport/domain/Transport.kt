package tycoon.transport.domain

interface Transport {
    fun load(cargoId: CargoId)
    fun unload(): CargoId
    fun goBack()
    fun startTrip(trip: Trip)
}
