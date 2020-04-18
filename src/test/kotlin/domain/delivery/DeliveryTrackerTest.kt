package domain.delivery

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.cargo.CargoId
import tycoon.transport.domain.delivery.DeliveryTracker

class DeliveryTrackerTest {

    private val tracker = DeliveryTracker()

    @Test fun `schedules cargo delivery`() {
        tracker.scheduleDeliveryOf(CargoId("1"))
        assertThat(tracker.allCargoesDelivered(), equalTo(false))
    }

    @Test fun `can be notified about delivery`() {
        tracker.scheduleDeliveryOf(CargoId("1"))
        tracker.cargoDelivered(CargoId("1"))
        assertThat(tracker.allCargoesDelivered(), equalTo(true))
    }

    @Test fun `ignores delivery of cargo that was not scheduled`() {
        tracker.scheduleDeliveryOf(CargoId("1"))
        tracker.cargoDelivered(CargoId("2"))
        assertThat(tracker.allCargoesDelivered(), equalTo(false))
    }

    @Test fun `delivering a single cargo does not deliver all`() {
        tracker.scheduleDeliveryOf(CargoId("1"))
        tracker.scheduleDeliveryOf(CargoId("2"))
        tracker.cargoDelivered(CargoId("2"))
        assertThat(tracker.allCargoesDelivered(), equalTo(false))
    }

    @Test fun `reports all cargoes delivered before anything scheduled`() {
        assertThat(tracker.allCargoesDelivered(), equalTo(true))
    }
}
