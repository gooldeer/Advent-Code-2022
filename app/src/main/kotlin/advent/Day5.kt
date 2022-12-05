package advent

import java.io.File

class PoorCraneOperator {

    fun readInitialStack(): List<ArrayDeque<Char>> = listOf(
        ArrayDeque(listOf('Z', 'N')),
        ArrayDeque(listOf('M', 'C', 'D')),
        ArrayDeque(listOf('P')),
        )

    fun readInitialStackFromFile(): List<ArrayDeque<Char>> {
        val stacks = mutableListOf<ArrayDeque<Char>>().apply {
            repeat(9) { add(ArrayDeque()) }
        }

        File("input/crates_positions.txt")
            .readLines()
            .map {
                val iterator = stacks.iterator()
                for (i in 1..it.length step 4) {
                    val stack = iterator.next()
                    val crate = it.get(i)
                    if (crate.isLetter()) {
                        stack.addFirst(crate)
                    }
                }
            }
        return stacks
    }


    fun readMovingScheme(): List<MovingEvent> = listOf(
        MovingEvent(1, 2, 1),
        MovingEvent(3, 1, 3),
        MovingEvent(2, 2, 1),
        MovingEvent(1, 1, 2),
    )

    fun readMovingSchemeFromFile(): List<MovingEvent> = File("input/crates_moving_scheme.txt")
        .readLines()
        .mapNotNull {
            "move (\\d+) from (\\d+) to (\\d+)"
                .toRegex()
                .find(it)
                ?.groupValues
                ?.let { it.subList(1, it.size) }
                ?.map { it.toInt() }
        }
        .map { MovingEvent(it[0], it[1], it[2]) }

    fun List<ArrayDeque<Char>>.rearrangleBy(scheme: List<MovingEvent>): List<ArrayDeque<Char>> = this.apply {
        scheme.forEach {
            val from = get(it.from - 1)
            val to = get(it.to - 1)

//            repeat(it.quantity) {
//                from.pop()?.let { crate -> to.push(crate) }
//            }
            from.popAll(it.quantity).let { crates -> to.addAll(crates) }
        }
    }

    fun goForIt() = readInitialStackFromFile().rearrangleBy(readMovingSchemeFromFile()).map { it.lastOrNull() }
}

data class MovingEvent(val quantity: Int, val from: Int, val to: Int)

fun <T> ArrayDeque<T>.push(element: T) = addLast(element) // returns Unit

fun <T> ArrayDeque<T>.pop() = removeLastOrNull()

fun <T> ArrayDeque<T>.popAll(n: Int) = takeLast(n).also { repeat(n) { removeLastOrNull() } }