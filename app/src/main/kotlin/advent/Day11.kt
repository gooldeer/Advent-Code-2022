package advent

import java.io.File
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt

class Day11 : AdventDay {

    private fun readInput() = File("input/day11.txt")
        .readLines()
        .chunked(7)
        .map { lines ->
            Monkey(
                id = lines[0].removePrefix("Monkey ").removeSuffix(":").toInt(),
                items = ArrayDeque(lines[1].removePrefix("  Starting items: ").split(", ").map { it.toLong() }),
                operation = {
                    val op = lines[2].removePrefix("  Operation: new = old ")
                    when {
                        op.startsWith("+") -> it + op.removePrefix("+ ").operationSuffixToPriority(it)
                        op.startsWith("*") -> it * op.removePrefix("* ").operationSuffixToPriority(it)
                        else -> it
                    }
                },
                divisibleBy = lines[3].removePrefix("  Test: divisible by ").toInt(),
                nextMonkey = {
                    when(it) {
                        true -> lines[4].removePrefix("    If true: throw to monkey ").toInt()
                        else -> lines[5].removePrefix("    If false: throw to monkey ").toInt()
                    }
                }
            )
        }

    private fun String.operationSuffixToPriority(priority: Long) = when(this) {
        "old" -> priority
        else -> this.toLong()
    }

    override fun run(): Any {
        val monkeys = readInput()
        val counts = mutableMapOf<Int, Long>()
        repeat(10000) {
            monkeys.forEach {
                counts.put(it.id, counts.getOrDefault(it.id, 0) + it.items.size)
                it.step(monkeys)
            }
        }
        return counts.values.sortedDescending().let { it[0] * it[1] }
    }

    data class Monkey(
        val id: Int,
        val items: ArrayDeque<Long>,
        val operation: (Long) -> Long,
        val divisibleBy: Int,
        val nextMonkey: (Boolean) -> Int,
    )

    private fun Monkey.step(pack: List<Monkey>) {
        while (items.isNotEmpty()) {
            items.removeFirstOrNull()?.let { inspectedItem ->
                val modBase = pack.fold(1L) { acc, monkey -> acc * monkey.divisibleBy }
                val newWorryLevel = operation(inspectedItem.mod(modBase))
                val nextMonkeyId = nextMonkey(newWorryLevel % divisibleBy == 0L)
                pack.find { it.id == nextMonkeyId }?.items?.addLast(newWorryLevel)
            }
        }
    }
}