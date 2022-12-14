/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package advent

import java.io.File

class Day1: AdventDay {

    private fun elfsSnacks() = File("input/fatties.txt")
        .readText()
        .split("\n\n")
        .asSequence()
        .mapIndexed { index, s ->
            index to s
                .split("\n")
                .mapNotNull { it.toIntOrNull() }
                .sum()
        }

    fun findTopFatties(top: Int) = elfsSnacks()
        .sortedByDescending { it.second }
        .take(top)

    override fun run() = findTopFatties(3).sumOf { it.second }
}
