package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.ShipmentIds

class ShipmentIdsTest {
    private val ids = ShipmentIds()

    @Test fun `generates a sequence of numbered shipment IDs`() {
        assertThat((1..3).map { ids.next() }, equalTo(listOf("1", "2", "3")))
    }
}
