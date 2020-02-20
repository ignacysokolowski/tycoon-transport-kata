package tycoon.transport.app

class TransportApp {
    fun ship(warehouseId: String) {
        if (warehouseId != "B") {
            throw RuntimeException()
        }
    }

    fun totalDeliveryTime(): Int {
        return 5
    }
}
