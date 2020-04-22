package domain.delivery

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.domain.Transport
import tycoon.transport.domain.delivery.Terminal
import tycoon.transport.domain.delivery.TerminalEmpty

class TerminalTest {

    private val terminal = Terminal()

    @Test fun `enqueues transport`() {
        val transport1: Transport = FakeTransport()
        val transport2: Transport = FakeTransport()
        terminal.enqueue(transport1)
        terminal.enqueue(transport2)
        assertThat(terminal.nextTransport(), equalTo(transport1))
        assertThat(terminal.nextTransport(), equalTo(transport2))
        assertThrows<TerminalEmpty> { terminal.nextTransport() }
    }

    @Test fun `is empty before any transport was enqueued`() {
        assertThrows<TerminalEmpty> { terminal.nextTransport() }
    }
}
