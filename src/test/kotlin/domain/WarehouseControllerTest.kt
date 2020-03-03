package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.CargoId
import tycoon.transport.domain.DeliveryListener
import tycoon.transport.domain.WarehouseController

class WarehouseControllerTest {

    private val deliveryListener = DeliverySpy()
    private val controller = WarehouseController(deliveryListener)

    @Test fun `unloads cargo from a transport that arrived and notifies about delivery`() {
        controller.transportArrived(FakeTransport())
        assertThat(deliveryListener.cargoesDelivered, equalTo(listOf(CargoId("dummy"))))
    }
}

class DeliverySpy : DeliveryListener {
    val cargoesDelivered = mutableListOf<CargoId>()

    override fun cargoDelivered(cargoId: CargoId) {
        cargoesDelivered.add(cargoId)
    }
}
