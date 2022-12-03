package advent

import java.io.File

class RockingThoseScissors {

    fun pointsForRound(oponent: Int, me: Int) = when {
        oponent == me -> DEUCE
        oponent - me == 1 || me - oponent > 1 -> LOSS
        else -> WIN
    } + me

    fun pointsForRoundFromResult(oponent: Int, result: Int) = when (result) {
        DEUCE -> oponent
        LOSS -> (oponent - 1).let { if (it < ROCK) SCISSORS else it }
        else -> (oponent + 1).let { if (it > SCISSORS) ROCK else it }
    } + result

    fun String.choiceToPoints() = when(this) {
        //choices
        "A","X" -> ROCK
        "B","Y" -> PAPER
        "C","Z" -> SCISSORS
        else -> 0
    }
    fun String.choiceAsResult() = when(this) {
        //result points
        "X" -> LOSS
        "Y" -> DEUCE
        "Z" -> WIN
        else -> 0
    }

    fun beatThemAll() = File("input/rockthefloor.txt")
        .readText()
        .split("\n")
        .asSequence()
        .map { it.split(" ") }
        .map { pointsForRoundFromResult(it[0].choiceToPoints(), it[1].choiceAsResult()) }
        .sum()

    companion object {
        const val WIN = 6
        const val LOSS = 0
        const val DEUCE = 3

        const val ROCK = 1
        const val PAPER = 2
        const val SCISSORS = 3
    }
}