package domain

import tycoon.transport.domain.CargoId
import tycoon.transport.domain.Transport
import tycoon.transport.domain.Trip

class FakeTransport : Transport {
    var cargoPickedUp: CargoId? = null
    var tripStarted: Trip? = null
    var goesBack = false

    override fun pickUp(cargoId: CargoId) {
        cargoPickedUp = cargoId
    }

    override fun startTrip(trip: Trip) {
        tripStarted = trip
    }

    override fun dropOff() = CargoId("dummy")

    override fun goBack() {
        goesBack = true
    }
}
