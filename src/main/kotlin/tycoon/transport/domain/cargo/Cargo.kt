package tycoon.transport.domain.cargo

import tycoon.transport.domain.LocationId

data class Cargo(val id: CargoId, val destination: LocationId)
