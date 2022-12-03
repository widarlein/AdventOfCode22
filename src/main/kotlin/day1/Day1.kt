package day1

import getResourceText

fun main(args: Array<String>) {

    val input = getResourceText("/day1/input")
    part1(input)
    part2(input)

}

fun part1(input: String) {
    val inventories = input.split("\n\n")
    val max = inventories.map { it.lines().sumOf { it.toInt() } }.max()

    println("part1 max = ${max}")
}

fun part2(input: String) {
    val inventories = input.split("\n\n")
    val top3Sum = inventories.map { it.lines().sumOf { it.toInt() } }.sorted().takeLast(3).sum()
    println("top3Sum = ${top3Sum}")
}

class Day1 {
}