package tycoon.transport.domain.carrier

data class Distance(private val hours: Int) {
    init {
        require(hours >= 0) { "Distance can not be negative" }
    }

    operator fun minus(other: Distance) = Distance(hours - other.hours)
}
