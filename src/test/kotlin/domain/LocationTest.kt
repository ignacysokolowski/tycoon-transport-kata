package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.domain.Location

class LocationTest {

    @Test fun `code can not be empty`() {
        val error = assertThrows<IllegalArgumentException> {
            Location("")
        }
        assertThat(error.message, equalTo("Location code can not be empty"))
    }
}
