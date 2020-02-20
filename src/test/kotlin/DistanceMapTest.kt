import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.domain.Distance
import tycoon.transport.domain.DistanceMap
import tycoon.transport.domain.WarehouseId
import tycoon.transport.domain.WarehouseUnknown

class DistanceMapTest {

    private val map = DistanceMap()

    @Test fun `tells the distance to a warehouse`() {
        map.addWarehouseWithDistance(Distance(5), WarehouseId("A"))
        assertThat(map.distanceTo(WarehouseId("A")), equalTo(5))
    }

    @Test fun `can not tell the distance to unknown warehouses`() {
        assertThrows<WarehouseUnknown> {
            map.distanceTo(WarehouseId("A"))
        }
    }
}
