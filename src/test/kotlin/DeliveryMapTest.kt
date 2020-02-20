import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.domain.DeliveryMap

class DeliveryMapTest {

    @Test fun `can not tell the distance to unknown warehouses`() {
        val map = DeliveryMap()
        assertThrows<RuntimeException> {
            map.distanceTo("B")
        }
    }
}
