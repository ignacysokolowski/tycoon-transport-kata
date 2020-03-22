package tycoon.transport.domain

class StopWatch(private val timeListener: TimeListener) {
    private var timeElapsed = 0

    fun countUntil(shouldStop: () -> Boolean) {
        while (!shouldStop()) {
            timeListener.tick()
            timeElapsed++
        }
    }

    fun timeElapsed() = timeElapsed
}
