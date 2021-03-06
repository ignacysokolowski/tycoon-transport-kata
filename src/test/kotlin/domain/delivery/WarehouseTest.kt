package domain.delivery

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.Location
import tycoon.transport.domain.cargo.CargoId
import tycoon.transport.domain.delivery.DeliveryListener
import tycoon.transport.domain.delivery.Warehouse

class WarehouseTest {

    private val deliveryListener = DeliverySpy()
    private val warehouse = Warehouse(Location("A"), deliveryListener)

    @Test fun `unloads cargo from a transport that arrived and notifies about delivery`() {
        warehouse.transportArrived(FakeTransport())
        assertThat(deliveryListener.cargoesDelivered, equalTo(listOf(CargoId("dummy"))))
    }
}

class DeliverySpy : DeliveryListener {
    val cargoesDelivered = mutableListOf<CargoId>()

    override fun cargoDelivered(cargoId: CargoId) {
        cargoesDelivered.add(cargoId)
    }
}
