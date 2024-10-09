package view

import model.Board
import model.Snake

class View(private val board: Board, private val snake: Snake) {
    private var oldSnake = snake.body.map { it.copy() }
    private var oldTarget = board.target.copy()
    private var boardArray = Array(board.size) { Array(board.size) { ' ' } }
    private var starting = true

    init {
        updateBoard()
    }

    fun render() {
        if (!snakeChanged() && oldTarget == board.target && !starting) return
        starting = false
        oldTarget = board.target.copy()
        oldSnake = snake.body.map { it.copy() }
        updateBoard()
        clearScreen()
        println("Use WASD keys to move. Press 'q' to quit.")
        printBoard()
    }

    private fun snakeChanged(): Boolean {
        val newSnake = snake.body
        if (newSnake.size != oldSnake.size) return true
        return newSnake.zip(oldSnake).any { (new, old) -> new != old }
    }

    private fun printBoard() {
        for (row in -1..board.size) {
            for (col in -1..board.size) {
                when (Pair(row, col)) {
                    Pair(-1, -1) -> print(" ┌")  // Top-left corner
                    Pair(-1, board.size) -> print("─┐")  // Top-right corner
                    Pair(board.size, -1) -> print(" └")  // Bottom-left corner
                    Pair(board.size, board.size) -> print("─┘")  // Bottom-right corner
                    else -> when {
                        row == -1 || row == board.size -> print("──")  // Top and bottom borders
                        col == -1 || col == board.size -> print(" │")  // Left and right borders
                        else -> print(" ${boardArray[row][col]}")  // Board content
                    }
                }
            }
            println()
        }
    }

    private fun updateBoard() {
        val head = snake.body.first()
        boardArray = Array(board.size) { Array(board.size) { ' ' } }
        boardArray[head.y][head.x] = 'O'
        snake.body.slice(1..<snake.body.size)
            .forEach { boardArray[it.y][it.x] = 'o' }
        boardArray[board.target.y][board.target.x] = '■'
    }

    private fun clearScreen() {
        print("\u001b[H\u001b[2J")
        System.out.flush()
    }

    fun showGameOver() {
        clearScreen()
        println("Game Over!")
    }
}