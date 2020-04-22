package tycoon.transport.domain.carrier

data class Distance(val hours: Int) {
    init {
        if (hours < 0) {
            throw IllegalArgumentException("Distance can not be negative")
        }
    }

    operator fun minus(other: Distance) = Distance(hours - other.hours)
}
