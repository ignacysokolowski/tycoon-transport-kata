package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.ShipmentIds

class ShipmentIdsTest {
    private val ids = ShipmentIds()

    @Test fun `first shipment ID is 1`() {
        assertThat(ids.next(), equalTo("1"))
    }
}
