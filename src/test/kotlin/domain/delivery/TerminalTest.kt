package domain.delivery

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.domain.delivery.Terminal
import tycoon.transport.domain.delivery.TerminalEmpty

class TerminalTest {

    @Test fun `is empty before any transport was enqueued`() {
        val terminal = Terminal()
        assertThrows<TerminalEmpty> { terminal.nextTransport() }
    }
}
