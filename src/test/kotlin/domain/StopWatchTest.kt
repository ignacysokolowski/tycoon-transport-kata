package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.StopWatch
import tycoon.transport.domain.TimeListener

class StopWatchTest {

    private val timeListener = TimeSpy()
    private val stopWatch = StopWatch(timeListener)

    @Test fun `counts time elapsed until instructed to stop counting`() {
        stopWatch.countUntil { timeListener.ticks == 3 }
        assertThat(stopWatch.timeElapsed(), equalTo(3))
    }

    @Test fun `no time elapsed before starting`() {
        assertThat(stopWatch.timeElapsed(), equalTo(0))
    }
}

class TimeSpy : TimeListener {
    var ticks = 0

    override fun tick() {
        ticks++
    }
}
