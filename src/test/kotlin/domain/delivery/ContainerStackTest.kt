package domain.delivery

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.Location
import tycoon.transport.domain.cargo.Cargo
import tycoon.transport.domain.cargo.CargoId
import tycoon.transport.domain.delivery.ContainerStack

class ContainerStackTest {

    @Test fun `stacks cargo and allows to pick up from the bottom`() {
        val cargoes = listOf(
            Cargo(CargoId("1"), Location("A")),
            Cargo(CargoId("2"), Location("B"))
        )
        val stack = ContainerStack()
        stack.put(cargoes)
        assertThat(stack.pickUpNext(), equalTo(cargoes[0]))
        assertThat(stack.pickUpNext(), equalTo(cargoes[1]))
    }
}
