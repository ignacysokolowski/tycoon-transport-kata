import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.app.TransportApp

class TransportAppTest {

    private val warehouses = mutableMapOf<String, Int>()
    private val app = TransportApp(warehouses)

    @Test fun `ships cargo to a warehouse`() {
        warehouses["B"] = 5
        app.ship("B")
        assertThat(app.totalDeliveryTime(), equalTo(5))
    }

    @Test fun `total delivery time is zero before anything has been shipped`() {
        assertThat(app.totalDeliveryTime(), equalTo(0))
    }

    @Test fun `can not ship to unknown destinations`() {
        warehouses["B"] = 5
        val exception = assertThrows<RuntimeException> {
            app.ship("X")
        }
        assertThat(exception.message, equalTo("Unknown destination"))
    }
}
