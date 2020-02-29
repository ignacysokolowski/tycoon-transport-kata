package tycoon.transport.domain

interface Transport {
    fun dropOff(): ShipmentId
    fun goBack()
}
