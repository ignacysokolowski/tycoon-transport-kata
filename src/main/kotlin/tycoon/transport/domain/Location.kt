package tycoon.transport.domain

data class Location(private val code: String) {
    init {
        require(code.isNotEmpty()) { "Location code can not be empty" }
    }
}
