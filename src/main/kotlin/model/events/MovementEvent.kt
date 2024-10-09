package model.events

import controller.GameController

class MovementEvent(val xOffset:Int, val yOffset:Int) : Event {
    override fun applyChanges(gameController: GameController){
        gameController.snake.updateFacing(this)
    }
}
