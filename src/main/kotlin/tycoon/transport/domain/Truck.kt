package tycoon.transport.domain

class Truck {
    private var distanceDriven = Distance(0)

    fun drive(distance: Distance) {
        distanceDriven += distance
    }

    fun distanceDriven() = distanceDriven
}
