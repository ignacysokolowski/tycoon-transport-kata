package domain

import tycoon.transport.domain.Cargo
import tycoon.transport.domain.CargoId
import tycoon.transport.domain.Transport

class FakeTransport : Transport {
    var cargoLoaded: CargoId? = null
    var goesBack = false

    override fun load(cargo: Cargo) {
        cargoLoaded = cargo.id
    }

    override fun unload() = CargoId("dummy")

    override fun goBack() {
        goesBack = true
    }
}
