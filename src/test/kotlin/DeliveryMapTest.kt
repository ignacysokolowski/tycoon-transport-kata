import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.domain.DeliveryMap
import tycoon.transport.domain.WarehouseUnknown

class DeliveryMapTest {

    private val map = DeliveryMap()

    @Test fun `tells the distance to a warehouse`() {
        assertThat(map.distanceTo("A"), equalTo(5))
    }

    @Test fun `allows adding new warehouses`() {
        map.addWarehouseWithDistance(3, "B")
        assertThat(map.distanceTo("B"), equalTo(3))
    }

    @Test fun `can not tell the distance to unknown warehouses`() {
        assertThrows<WarehouseUnknown> {
            map.distanceTo("B")
        }
    }
}
