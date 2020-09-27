package minesweeper

fun main() {
    println("How many mines do you want on the field?")
    val minesRequired = readLine()!!.toInt()
    val game = Game(9, minesRequired)
    print(game.render())
}
