package tycoon.transport.domain

data class Distance(val hours: Int) {
    init {
        if (hours < 0) {
            throw IllegalArgumentException()
        }
    }

    operator fun plus(other: Distance) = Distance(hours + other.hours)
    operator fun minus(other: Distance) = Distance(hours - other.hours)
}
