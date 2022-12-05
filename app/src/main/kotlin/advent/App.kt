package advent

fun main(args: Array<String>) {
    //Day 1
    println(
        FatSanta()
            .findTopFatties(3)
            .sumOf { it.second }
    )
    //Day 2
    println(RockingThoseScissors().beatThemAll())
    //Day 3
    println(WhoHasBeerInRucksack().findBadges().sum())
    //Day 4
    println(CleaningOptimizer().findUselessPairs().count { it })
    //Day 5
    println(PoorCraneOperator().goForIt().joinToString(separator = ""))
}
