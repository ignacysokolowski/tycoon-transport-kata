package tycoon.transport.app

import tycoon.transport.domain.Transport

class WarehouseController {
    fun transportArrived(transport: Transport) {
        transport.dropOff()
    }
}
