package model

class Position(var x: Int, var y: Int){
    fun copy(): Position {
        return Position(x, y)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Position

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    override fun toString(): String {
        return "Position(x=$x, y=$y)"
    }

    fun isOutOfBounds(size: Int): Boolean {
        return x < 0 || y < 0 || x >= size || y >= size
    }
}