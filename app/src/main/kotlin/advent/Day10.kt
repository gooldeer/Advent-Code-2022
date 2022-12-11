package advent

import java.io.File
import kotlin.math.abs

class Day10: AdventDay {

    private fun readInput() = File("input/day10.txt").readLines()

    override fun run(): Any {
        val cycles = mutableMapOf<Int, Int>()
        var currentCicle = 0
        var currentRegister = 1

        cycles[currentCicle] = currentRegister

        readInput()
            .map {
                when {
                    it == "noop" -> 1 to 0
                    else -> 2 to it.removePrefix("addx ").toInt()
                }
            }
            .forEach {
                currentCicle += it.first
                currentRegister += it.second
                cycles[currentCicle] = currentRegister
            }

        var drawn = ""

        repeat(currentCicle) { index ->
            drawn += cycles.getPixel(index)
        }

//        var sum = 0
//        for (i in 20..220 step 40) {
//            val signal = (cycles[i - 1] ?: cycles[i - 2] ?: 0) * i
//            sum += signal
//        }
//        return
        return "\n" + drawn.chunked(40).joinToString("\n")
    }

    private fun Map<Int, Int>.getPixel(index: Int) : String {
        val currentRegister: Int = get(index) ?: get(index - 1) ?: 0
        return if (index % 40 in (currentRegister - 1)..(currentRegister + 1) ) {
            "#"
        } else {
            "."
        }
    }
}