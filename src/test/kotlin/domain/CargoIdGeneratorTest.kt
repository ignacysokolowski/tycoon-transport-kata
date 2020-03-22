package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.CargoId
import tycoon.transport.domain.CargoIdGenerator

class CargoIdGeneratorTest {
    private val generator = CargoIdGenerator()

    @Test fun `generates a sequence of numbered cargo IDs`() {
        assertThat(
            (1..3).map { generator.next() },
            equalTo(listOf(CargoId("1"), CargoId("2"), CargoId("3")))
        )
    }
}
