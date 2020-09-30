package minesweeper

fun main() {
    println("How many mines do you want on the field?")
    val minesRequired = readLine()!!.toInt()
    val game = Game(9, minesRequired)
    print(game.render())
    while (game.state == GameState.RUNNING) {
        println("Set/unset mines marks or claim a cell as free:")
        commandParser(readLine()!!, game)
        println(game.render())
    }
    if (game.state == GameState.WON) {
        println("Congratulations! You found all the mines!")
    } else {
        println("You stepped on a mine and failed!")
    }

}

fun commandParser (input: String, game: Game) {
    val commandSegments = input.trim().split(" ")
    val coordinates = commandSegments.take(2).reversed().map { it.toInt() - 1 }
    when (commandSegments[2]) {
        "free" -> game.exploreCoordinate(coordinates)
        "mine" -> game.markCoordinate(coordinates)
    }
}