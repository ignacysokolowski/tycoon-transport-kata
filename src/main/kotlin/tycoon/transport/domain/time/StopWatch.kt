package tycoon.transport.domain.time

class StopWatch(private val timeListener: TimeListener, private val timeLimit: Int) {
    private var timeElapsed = 0

    fun countUntil(shouldStop: () -> Boolean) {
        while (!shouldStop()) {
            if (timeElapsed >= timeLimit) {
                throw RuntimeException("Exceeded the time limit of $timeLimit")
            }
            timeListener.tick()
            timeElapsed++
        }
    }

    fun timeElapsed() = timeElapsed
}
