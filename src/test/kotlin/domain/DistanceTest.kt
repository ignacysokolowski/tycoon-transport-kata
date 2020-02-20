package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.Distance

class DistanceTest {

    @Test fun `can be added to another distance`() {
        assertThat(Distance(2) + Distance(3), equalTo(Distance(5)))
    }
}