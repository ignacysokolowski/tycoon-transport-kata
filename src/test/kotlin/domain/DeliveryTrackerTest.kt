package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.CargoId
import tycoon.transport.domain.DeliveryTracker

class DeliveryTrackerTest {

    private val tracker = DeliveryTracker()

    @Test fun `reports all cargoes delivered before anything scheduled`() {
        assertThat(tracker.allCargoesDelivered(), equalTo(true))
    }

    @Test fun `schedules cargo delivery`() {
        tracker.scheduleDeliveryOf(CargoId("1"))
        assertThat(tracker.allCargoesDelivered(), equalTo(false))
    }
}
