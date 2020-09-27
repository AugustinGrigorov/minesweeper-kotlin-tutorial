package minesweeper

import kotlin.random.Random

class Game(private val boardSize: Int, minesRequired: Int) {
    private var mines: HashMap<List<Int>, Boolean> = hashMapOf()
    private var numbers: HashMap<List<Int>, Boolean> = hashMapOf()
    private var marked: HashMap<List<Int>, Boolean> = hashMapOf()

    init {
        if (minesRequired > boardSize * boardSize) {
            throw Exception("Can't fit this many mines on the board")
        }
        for (i in 0 until minesRequired) {
            var coordinate = listOf(Random.nextInt(0, boardSize), Random.nextInt(0, boardSize))
            while (coordinate in mines) {
                coordinate = listOf(Random.nextInt(0, boardSize), Random.nextInt(0, boardSize))
            }
            mines[coordinate] = true
        }
    }

    fun render(): String {
        var result = " │123456789│\n—│—————————│\n"
        for (i in 0 until boardSize) {
            result += "${i + 1}|"
            for (j in 0 until boardSize) {
                var coordinate = listOf(i, j)
                result += when (coordinate) {
                    in marked -> "*"
                    in mines -> "."
                    else -> {
                        val surroundingMines = getSurroundingMines(i, j)
                        if(surroundingMines == 0) {
                            "."
                        } else {
                            numbers[coordinate] = true
                            surroundingMines.toString()
                        }
                    }
                }
            }
            result += "|\n"
        }
        result += "—│—————————│\n"
        return result
    }

    private fun getSurroundingMines(height: Int, width: Int): Int {
        var mineCount = 0
        for (i in maxOf(0, height - 1) until minOf(boardSize, height + 2)) {
            for (j in maxOf(0, width - 1) until minOf(boardSize, width + 2)) {
                var coordinate = listOf(i, j)
                if (coordinate in mines) mineCount++
            }
        }
        return mineCount
    }

    fun markCoordinate(coordinate: List<Int>): Boolean {
        if (coordinate in numbers) {
            return false
        }
        if (coordinate in marked) {
            marked.remove(coordinate)
        } else {
            marked[coordinate] = true;
        }
        return true
    }

    fun won(): Boolean {
        return mines == marked
    }
}