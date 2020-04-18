package domain.carrier

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.domain.carrier.Distance

class DistanceTest {

    @Test fun `can not be negative`() {
        val error = assertThrows<IllegalArgumentException> {
            Distance(-1)
        }
        assertThat(error.message, equalTo("Distance can not be negative"))
    }

    @Test fun `can be added to another distance`() {
        assertThat(Distance(2) + Distance(3), equalTo(Distance(5)))
    }

    @Test fun `can be subtracted from another distance`() {
        assertThat(Distance(3) - Distance(2), equalTo(Distance(1)))
    }
}
