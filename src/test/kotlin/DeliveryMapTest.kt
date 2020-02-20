import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.domain.DeliveryMap
import tycoon.transport.domain.WarehouseUnknown

class DeliveryMapTest {

    private val map = DeliveryMap()

    @Test fun `tells the distance to a warehouse`() {
        map.addWarehouseWithDistance(5, "A")
        assertThat(map.distanceTo("A"), equalTo(5))
    }

    @Test fun `can not tell the distance to unknown warehouses`() {
        assertThrows<WarehouseUnknown> {
            map.distanceTo("A")
        }
    }
}
