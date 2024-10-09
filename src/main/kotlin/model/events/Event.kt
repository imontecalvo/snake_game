package model.events

import controller.GameController

interface Event {
    fun applyChanges(gameController: GameController)
}