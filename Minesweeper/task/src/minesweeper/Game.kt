package minesweeper

import kotlin.random.Random

class Game(private val boardSize: Int, minesRequired: Int) {
    private val board: Array<Array<Int>> = Array(boardSize){Array(boardSize){0}}

    init {
        if (minesRequired > boardSize * boardSize) {
            throw Exception("Can't fit this many mines on the board")
        }
        for (i in 0 until minesRequired) {
            var randomHeight = Random.nextInt(0, boardSize)
            var randomWidth = Random.nextInt(0, boardSize)
            while (board[randomHeight][randomWidth] == 1) {
                randomHeight = Random.nextInt(0, boardSize)
                randomWidth = Random.nextInt(0, boardSize)
            }
            board[randomHeight][randomWidth] = 1
        }
    }

    fun render() :String {
        var result = ""
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                result += when {
                    board[i][j] == 1 -> "X"
                    board[i][j] == 0 -> "."
                    else -> throw Exception("Unsupported value in grid")
                }
            }
            result += "\n"
        }
        return result
    }
}