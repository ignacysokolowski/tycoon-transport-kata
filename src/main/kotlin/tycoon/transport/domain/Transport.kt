package tycoon.transport.domain

interface Transport {
    fun unload(): CargoId
    fun goBack()
    fun load(cargo: Cargo)
}
