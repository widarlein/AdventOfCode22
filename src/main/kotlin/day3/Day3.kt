package day3

import getResourceText

fun main(args: Array<String>) {

    val input = getResourceText("/day3/input")
    part1(input)
    part2(input)

}

fun part1(input: String) {
    val sum = input.lines().sumOf {
        (it.substring(0, it.length/2).toSet().intersect(it.substring(it.length/2).toSet()))
            .first().toPrio()
    }
    println("sum = ${sum}")
}

fun Char.toPrio(): Int = if (this.isLowerCase()) this.code - 96 else this.code - 38

fun part2(input: String) {
    val sum = input.lines().chunked(3).sumOf { group ->
        val (elf1, elf2, elf3) = group
        val badge = elf1.toSet().intersect(elf2.toSet()).intersect(elf3.toSet())
        badge.first().toPrio()
    }

    println("sum = ${sum}")
}