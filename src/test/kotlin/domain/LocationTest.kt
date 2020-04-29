package domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.domain.Location

class LocationTest {

    @Test fun `code can not be empty`() {
        assertThrows<IllegalArgumentException> {
            Location("")
        }
    }
}
