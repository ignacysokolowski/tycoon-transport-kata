package domain

import tycoon.transport.domain.CargoId
import tycoon.transport.domain.Transport
import tycoon.transport.domain.Trip

class FakeTransport : Transport {
    var cargoLoaded: CargoId? = null
    var tripStarted: Trip? = null
    var goesBack = false

    override fun load(cargoId: CargoId) {
        cargoLoaded = cargoId
    }

    override fun startTrip(trip: Trip) {
        tripStarted = trip
    }

    override fun unload() = CargoId("dummy")

    override fun goBack() {
        goesBack = true
    }
}
