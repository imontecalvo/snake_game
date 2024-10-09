import controller.GameController
import model.events.Event
import kotlinx.coroutines.channels.Channel
import model.Board
import model.KeyListener
import model.Snake
import view.View

fun main(args: Array<String>) {
    val boardSize = args.firstOrNull()?.toIntOrNull() ?: 20
    val board = Board(boardSize)
    val snake = Snake()
    val eventsChannel: Channel<Event> = Channel(Channel.UNLIMITED)
    val keyListener = KeyListener(eventsChannel)
    val view = View(board, snake)

    val gameController = GameController(snake, board, eventsChannel, keyListener, view)
    gameController.start()
}