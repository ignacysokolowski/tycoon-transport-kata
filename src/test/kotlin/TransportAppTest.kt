import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.app.TransportApp

class TransportAppTest {

    @Test fun `it takes 5 hours to deliver cargo to Warehouse B`() {
        val app = TransportApp()
        app.ship("B")
        assertThat(app.totalDeliveryTime(), equalTo(5))
    }
}
