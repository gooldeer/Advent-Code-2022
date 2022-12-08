package advent

import java.io.File

class Day4 : AdventDay {

    fun readInput() = File("input/cleaning.txt").readLines()

    fun transformToPairs(lines: List<String>) = lines.map { it.lineToElfsPair() }

    fun findUselessPairs() = transformToPairs(readInput())
        .map { it.kindOfUselessPairOfElfs() }

    fun Pair<IntRange, IntRange>.uselessPairOfElfs() = first.containsAll(second) || second.containsAll(first)

    fun Pair<IntRange, IntRange>.kindOfUselessPairOfElfs() = first.any(second::contains) || second.any(first::contains)

    fun IntRange.containsAll(range: IntRange) = toList().containsAll(range.toList())

    fun String.lineToElfsPair() = split(",")
        .map { it
            .split("-")
            .let { it[0].toInt()..it[1].toInt() }
        }
        .let { it[0] to it[1] }

    override fun run() = findUselessPairs().count { it }
}