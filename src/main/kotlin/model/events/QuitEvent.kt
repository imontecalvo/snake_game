package model.events

import controller.GameController

class QuitEvent: Event {
    override fun applyChanges(gameController: GameController) {
        gameController.quit()
    }
}