package model

import model.events.MovementEvent

class Snake {
    private val initPosition = Position(0,0)
    val body: MutableList<Position> = mutableListOf(initPosition)
    private var facing = MovementEvent(1,0)

    fun nextMove() {
        val head = body.first()
        val newHead = Position(head.x + facing.xOffset, head.y + facing.yOffset)
        body.removeLast()
        body.addFirst(newHead)
    }

    fun updateFacing(movementEvent: MovementEvent) {
        facing = movementEvent
    }

    fun upgrade() {
        val tail = body.last().copy()
        nextMove()
        body.add(tail)
    }
}