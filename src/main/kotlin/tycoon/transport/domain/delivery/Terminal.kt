package tycoon.transport.domain.delivery

class Terminal {
    fun nextTransport() {
        throw TerminalEmpty()
    }
}
