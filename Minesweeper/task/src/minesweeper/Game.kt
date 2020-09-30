package minesweeper

import kotlin.random.Random

enum class GameState {
    WON, LOST, RUNNING
}

class Game(private val boardSize: Int, minesRequired: Int) {
    private var mines: MutableSet<List<Int>> = mutableSetOf()
    private var numbers: HashMap<List<Int>, Int> = hashMapOf()
    private var marked: MutableSet<List<Int>> = mutableSetOf()
    private var revealed: MutableSet<List<Int>> = mutableSetOf()

    var state: GameState = GameState.RUNNING

    init {
        if (minesRequired > boardSize * boardSize) {
            throw Exception("Can't fit this many mines on the board")
        }
        for (i in 0 until minesRequired) {
            var coordinate = listOf(Random.nextInt(0, boardSize), Random.nextInt(0, boardSize))
            while (mines.contains(coordinate)) {
                coordinate = listOf(Random.nextInt(0, boardSize), Random.nextInt(0, boardSize))
            }
            mines.add(coordinate)
        }
        for (i in 0 until  boardSize) {
            for (j in 0 until boardSize) {
                val coordinate = listOf(i, j)
                var surroundingMines = 0
                forSurroundingCells(coordinate) { nearbyCoordinate: List<Int> ->
                    if (mines.contains(nearbyCoordinate)) surroundingMines++
                }
                if (surroundingMines > 0) numbers[coordinate] = surroundingMines
            }
        }
    }

    fun render(): String {
        var result = " │123456789│\n—│—————————│\n"
        for (i in 0 until boardSize) {
            result += "${i + 1}|"
            for (j in 0 until boardSize) {
                var coordinate = listOf(i, j)
                result += when {
                    marked.contains(coordinate) -> "*"
                    !revealed.contains(coordinate) -> "."
                    coordinate in numbers -> numbers[coordinate]
                    mines.contains(coordinate) -> "X"
                    else -> "/"
                }
            }
            result += "|\n"
        }
        result += "—│—————————│\n"
        return result
    }

    fun markCoordinate(coordinate: List<Int>): Boolean {
        if (revealed.contains(coordinate)) {
            return false
        }
        if (marked.contains(coordinate)) {
            marked.remove(coordinate)
        } else {
            marked.add(coordinate);
            if (mines == marked) state = GameState.WON
        }
        return true
    }

    fun exploreCoordinate(coordinate: List<Int>) {
        if (mines.contains(coordinate)) {
            state = GameState.LOST
        }
        if (marked.contains(coordinate)) {
            marked.remove(coordinate)
        }
        revealed.add(coordinate)
        if (revealed.size == boardSize*boardSize - mines.size) state = GameState.WON
        if (!numbers.contains(coordinate)) {
            forSurroundingCells(coordinate) { nearbyCoordinate: List<Int> ->
                if (!mines.contains(nearbyCoordinate) && !revealed.contains(nearbyCoordinate)) {
                    exploreCoordinate(nearbyCoordinate)
                }
            }
        }
    }

    private fun forSurroundingCells(startingPoint: List<Int>, action: (List<Int>) -> Any) {
        val height = startingPoint[0]
        val width = startingPoint[1]
        for (i in maxOf(0, height - 1) until minOf(boardSize, height + 2)) {
            for (j in maxOf(0, width - 1) until minOf(boardSize, width + 2)) {
                var coordinate = listOf(i, j)
                if (coordinate != startingPoint) action(coordinate)
            }
        }
    }
}