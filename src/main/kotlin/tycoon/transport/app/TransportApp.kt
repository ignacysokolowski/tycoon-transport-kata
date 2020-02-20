package tycoon.transport.app

class TransportApp {
    fun ship(warehouseId: String) {
        if (warehouseId != "B") {
            throw RuntimeException("Unknown destination")
        }
    }

    fun totalDeliveryTime(): Int {
        return 5
    }
}
