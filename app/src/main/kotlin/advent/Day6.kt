package advent

import java.io.File

class Day6: AdventDay {

    fun readInput() = File("input/day6.txt").readText()

    fun String.findPacketMarker(): Int? {
        val buffer = mutableListOf<Char>()
        forEachIndexed { index, c ->
            if (buffer.contains(c)) {
                val i = buffer.indexOf(c)
                buffer.removeAll { buffer.indexOf(it) <= i }
            }
            buffer.add(c)
            if (buffer.size == MARKER_LENGTH) {
                return index
            }
        }
        return null
    }

    override fun run(): Any = (readInput().findPacketMarker() ?: 0) + 1

    companion object {
        const val MARKER_LENGTH = 14
    }
}