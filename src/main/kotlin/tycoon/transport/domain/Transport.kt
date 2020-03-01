package tycoon.transport.domain

interface Transport {
    fun dropOff(): CargoId
    fun goBack()
}
