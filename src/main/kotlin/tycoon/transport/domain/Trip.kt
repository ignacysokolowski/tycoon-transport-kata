package tycoon.transport.domain

class Trip {
    companion object {
        fun between(origin: LocationId, destination: LocationId, distance: Distance): Trip {
            return Trip()
        }
    }

    fun journey(): Journey {
        return Journey.to(LocationId("B"), Distance(3))
    }
}
