package tycoon.transport.domain

class StopWatch {
    private var timeElapsed = 0

    fun countUntil(shouldStop: () -> Boolean) {
        while (!shouldStop()) {
            timeElapsed++
        }
    }

    fun timeElapsed() = timeElapsed
}
