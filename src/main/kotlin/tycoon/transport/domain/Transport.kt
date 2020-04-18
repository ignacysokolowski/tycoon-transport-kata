package tycoon.transport.domain

import tycoon.transport.domain.cargo.Cargo
import tycoon.transport.domain.cargo.CargoId

interface Transport {
    fun load(cargo: Cargo)
    fun unload(): CargoId
}
