import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.app.TransportApp

class TransportAppTest {

    private val app = TransportApp()

    @Test fun `it takes 5 hours to deliver cargo to a warehouse`() {
        app.addWarehouse("B")
        app.ship("B")
        assertThat(app.totalDeliveryTime(), equalTo(5))
    }

    @Test fun `can not ship to unknown destinations`() {
        app.addWarehouse("B")
        val exception = assertThrows<RuntimeException> {
            app.ship("X")
        }
        assertThat(exception.message, equalTo("Unknown destination"))
    }
}
