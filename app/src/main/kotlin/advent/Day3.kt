package advent

import java.io.File

class WhoHasBeerInRucksack {

    fun readInput() = File("input/rucksacks.txt")
        .readText()
        .splitToSequence("\n")

    fun findWrongs() = readInput()
        .map { findSameItemsInRucksack(it) }
        .map { it?.toPoins() ?: 0 }

    fun findBadges() = readInput()
        .chunked(3)
        .map { it.findSameItem() }
        .map { it?.toPoins() ?: 0 }

    fun findSameItemsInRucksack(rucksack: String): Char? = rucksack
        .chunked(rucksack.length / 2)
        .findSameItem()

    fun List<String>.findSameItem() = joinToString()
        .find { all { s -> s.contains(it)} }
}

fun Char.toPoins(): Int {
    val lower = when {
        isUpperCase() -> 64 - 26
        else -> 96
    }
    return this.code - lower
}