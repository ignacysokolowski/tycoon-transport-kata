package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.StopWatch

class StopWatchTest {

    private val stopWatch = StopWatch()

    @Test fun `increases the time elapsed until should it stop`() {
        var ticks = 0
        stopWatch.countUntil { ticks++; ticks > 3 }
        assertThat(stopWatch.timeElapsed(), equalTo(3))
    }

    @Test fun `no time elapsed before starting`() {
        assertThat(stopWatch.timeElapsed(), equalTo(0))
    }
}
