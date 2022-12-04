package day4

import getResourceText

fun main(args: Array<String>) {

    val input = getResourceText("/day4/input")
    part1(input)
    part2(input)

}

fun part1(input: String) {
    val sum = input.lines().sumOf {
        val (sections1, sections2) = it.split(",")
        val range1 = sections1.toRange()
        val range2 = sections2.toRange()
        val intersection = range1.intersect(range2)
        if (intersection == range1.toSet() || intersection == range2.toSet()) 1 else 0 as Int
    }

    println("sum = ${sum}")
}

fun String.toRange(): IntRange {
    val (lower, upper) = this.split("-")
    return lower.toInt()..upper.toInt()
}


fun part2(input: String) {
    val sum = input.lines().sumOf {
        val (sections1, sections2) = it.split(",")
        val range1 = sections1.toRange()
        val range2 = sections2.toRange()
        val intersection = range1.intersect(range2)
        if (intersection.isNotEmpty()) 1 else 0 as Int
    }

    println("sum = ${sum}")

}