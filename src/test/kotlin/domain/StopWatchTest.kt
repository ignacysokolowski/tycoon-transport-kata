package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import tycoon.transport.domain.StopWatch
import tycoon.transport.domain.TimeListener

class StopWatchTest {

    private val timeListener = TimeSpy()
    private val stopWatch = StopWatch(timeListener as TimeListener)

    @Test fun `increases the time elapsed until should it stop`() {
        var ticks = 0
        stopWatch.countUntil { ticks++; ticks > 3 }
        assertThat(stopWatch.timeElapsed(), equalTo(3))
    }

    @Test fun `notifies the time listener on every tick of the clock`() {
        var ticks = 0
        stopWatch.countUntil { ticks++; ticks > 3 }
        assertThat(timeListener.ticks, equalTo(3))
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
