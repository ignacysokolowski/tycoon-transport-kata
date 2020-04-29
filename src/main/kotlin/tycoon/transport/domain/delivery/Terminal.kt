package tycoon.transport.domain.delivery

import java.util.ArrayDeque
import tycoon.transport.domain.Transport

class Terminal {
    private val queue = ArrayDeque<Transport>()

    fun enqueue(transport: Transport) {
        queue.add(transport)
    }

    fun nextTransport(): Transport {
        return try {
            queue.pop()
        } catch (e: NoSuchElementException) {
            throw TerminalEmpty()
        }
    }
}
