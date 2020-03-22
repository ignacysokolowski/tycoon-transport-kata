package domain

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tycoon.transport.domain.StopWatch
import tycoon.transport.domain.TimeListener

class StopWatchTest {

    private val timeListener = TimeSpy()
    private val stopWatch = StopWatch(timeListener, timeLimit = 10)

    @Test fun `counts time elapsed until instructed to stop counting`() {
        stopWatch.countUntil { timeListener.ticks == 3 }
        assertThat(stopWatch.timeElapsed(), equalTo(3))
    }

    @Test fun `does not count forever, breaks at the time limit`() {
        val error = assertThrows<RuntimeException> {
            stopWatch.countUntil { false }
        }
        assertThat(error.message, equalTo("Exceeded the time limit of 10"))
        assertThat(stopWatch.timeElapsed(), equalTo(10))
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
