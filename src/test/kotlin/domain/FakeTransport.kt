package domain

import tycoon.transport.domain.Transport
import tycoon.transport.domain.cargo.Cargo
import tycoon.transport.domain.cargo.CargoId

class FakeTransport : Transport {
    var cargoLoaded: CargoId? = null

    override fun load(cargo: Cargo) {
        cargoLoaded = cargo.id
    }

    override fun unload() = CargoId("dummy")
}
