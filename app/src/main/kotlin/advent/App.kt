package advent

fun main(args: Array<String>) {
    println(
        FatSanta()
            .findTopFatties(3)
            .sumOf { it.second }
    )
    println(RockingThoseScissors().beatThemAll())
    println(WhoHasBeerInRucksack().findBadges().sum())
    println(CleaningOptimizer().findUselessPairs().count { it })
}
