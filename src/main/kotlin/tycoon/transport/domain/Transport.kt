package tycoon.transport.domain

interface Transport {
    fun load(cargo: Cargo)
    fun unload(): CargoId
    fun goBack()
}
