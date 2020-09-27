package minesweeper

class Game(private val boardSize: Int) {
    private val board: Array<Array<Int>> = Array(boardSize){Array(boardSize){0}}

    init {
        board[3][5] = 1
        board[5][4] = 1
        board[6][6] = 1
        board[2][1] = 1
    }

    fun render() :String {
        var result = ""
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                result += board[i][j]
            }
            result += "\n"
        }
        return result
    }
}