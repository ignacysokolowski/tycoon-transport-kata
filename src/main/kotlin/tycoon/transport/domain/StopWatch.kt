package tycoon.transport.domain

class StopWatch {
    var timeElapsed = 0

    fun countUntil(shouldStop: () -> Boolean) {
        while (!shouldStop()) {
            timeElapsed++
        }
    }
}
