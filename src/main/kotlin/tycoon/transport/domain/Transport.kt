package tycoon.transport.domain

interface Transport {
    fun pickUp(cargoId: CargoId)
    fun dropOff(): CargoId
    fun goBack()
}
