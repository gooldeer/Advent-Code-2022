package advent

fun main(args: Array<String>) {
    val days = listOf(
        Day1(),
        Day2(),
        Day3(),
        Day4(),
        Day5(),
        Day6(),
        Day7(),
        Day11(),
    )
    days.forEach { println("${it.javaClass.name} : ${it.run()}") }
}
