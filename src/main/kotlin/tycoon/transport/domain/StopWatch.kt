package tycoon.transport.domain

class StopWatch(private val timeListener: TimeListener, private val timeLimit: Int) {
    private var timeElapsed = 0

    fun countUntil(shouldStop: () -> Boolean) {
        while (!shouldStop()) {
            if (timeElapsed >= timeLimit) {
                throw RuntimeException()
            }
            timeListener.tick()
            timeElapsed++
        }
    }

    fun timeElapsed() = timeElapsed
}
