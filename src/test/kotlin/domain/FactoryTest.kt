package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.Factory

class FactoryTest {

    @Test fun `has no shipments waiting`() {
        val factory = Factory()
        assertThat(factory.shipmentsWaiting(), equalTo(emptyList()))
    }
}
