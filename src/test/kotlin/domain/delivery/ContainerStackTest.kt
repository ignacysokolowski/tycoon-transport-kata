package domain.delivery

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.domain.Location
import tycoon.transport.domain.cargo.Cargo
import tycoon.transport.domain.cargo.CargoId
import tycoon.transport.domain.delivery.AllCargoPickedUp
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

    @Test fun `does not allow to pick up anymore after all cargo have been picked up`() {
        val stack = ContainerStack()
        stack.put(listOf(Cargo(CargoId("1"), Location("A"))))
        stack.pickUpNext()
        assertThrows<AllCargoPickedUp> { stack.pickUpNext() }
    }
}
