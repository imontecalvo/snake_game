package controller

import model.Board
import model.KeyListener
import model.Snake
import view.View
import model.events.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class GameController(
    val snake: Snake,
    private val board: Board,
    private val eventsChannel: Channel<Event>,
    private val keyListener: KeyListener,
    private val view: View
) {
    private var quit = false
    private val gameScope = CoroutineScope(Dispatchers.Default)

    fun start() {
        gameScope.launch { keyListener.listen() }
        while (!quit && !board.isOver(snake)) {
            view.render()
            if (board.isLevelFinished(snake)) {
                board.nextLevel()
                snake.upgrade()
                continue
            }
            Thread.sleep(100)
            eventsChannel.tryReceive().getOrNull()?.applyChanges(this)
            snake.nextMove()
        }
        view.showGameOver()
        keyListener.stop()
        gameScope.cancel()
    }

    fun quit() {
        quit = true
    }
}