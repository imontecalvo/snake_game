package model

class Board(val size: Int) {
    var target = calculateTargetPosition()

    private fun calculateTargetPosition(): Position {
        //TODO: improve this method to avoid placing the target on the snake
        return Position((Math.random() * (size - 1)).toInt(), (Math.random() * (size - 1)).toInt())
    }

    fun isOver(snake: Snake): Boolean {
        return snake.body.first().isOutOfBounds(size) || isSnakeColliding(snake)
    }

    private fun isSnakeColliding(snake: Snake): Boolean {
        val head = snake.body.first()
        return snake.body.drop(1).any { it == head }
    }

    fun isLevelFinished(snake: Snake): Boolean {
        return snake.body.first() == target
    }

    fun nextLevel() {
        target = Position((Math.random() * (size - 1)).toInt(), (Math.random() * (size - 1)).toInt())
    }
}