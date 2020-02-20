package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.Distance
import tycoon.transport.domain.Truck

class TruckTest {

    @Test fun `has not driven yet`() {
        val truck = Truck()
        assertThat(truck.distanceDriven(), equalTo(Distance(0)))
    }
}
