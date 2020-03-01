package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.CargoId
import tycoon.transport.domain.CargoIds

class CargoIdsTest {
    private val ids = CargoIds()

    @Test fun `generates a sequence of numbered cargo IDs`() {
        assertThat(
            (1..3).map { ids.next() },
            equalTo(listOf(CargoId("1"), CargoId("2"), CargoId("3")))
        )
    }
}
