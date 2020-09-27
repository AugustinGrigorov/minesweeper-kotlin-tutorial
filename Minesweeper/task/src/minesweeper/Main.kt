package minesweeper

fun main() {
    println("How many mines do you want on the field?")
    val minesRequired = readLine()!!.toInt()
    val game = Game(9, minesRequired)
    print(game.render())
    while (!game.won()) {
        println("Set/delete mine marks (x and y coordinates):")
        val coordinates = readLine()!!.trim().split(" ").reversed().map { it.toInt() - 1 }
        if (game.markCoordinate(coordinates)) {
            print(game.render())
        } else {
            println("There is a number here!")
        }
    }
    println("Congratulations! You found all the mines!")
}
