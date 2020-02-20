package tycoon.transport.domain

data class Distance(val hours: Int) {
    operator fun plus(other: Distance) = Distance(hours + other.hours)
}
