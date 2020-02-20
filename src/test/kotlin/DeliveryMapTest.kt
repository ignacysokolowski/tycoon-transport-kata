import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.domain.DeliveryMap
import tycoon.transport.domain.WarehouseUnknown

class DeliveryMapTest {

    private val map = DeliveryMap()

    @Test fun `can not tell the distance to unknown warehouses`() {
        assertThrows<WarehouseUnknown> {
            map.distanceTo("B")
        }
    }
}
