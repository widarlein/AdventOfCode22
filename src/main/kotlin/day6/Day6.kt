package day6

import getResourceText

fun main(args: Array<String>) {

    val input = getResourceText("/day6/input")

    part1(input)
    part2(input)
}

fun part1(input: String) {
    val index = detectDistinct(input, 4)
    println("index = ${index}")
}

fun detectDistinct(input: String, distinctCount: Int): Int {
    val window = mutableListOf<Char>()
    input.forEachIndexed { index, c ->
        window.add(c)
        if (window.size == distinctCount) {
            if (window.toSet().size == distinctCount) {
                println("index = ${index + 1}")
                return index + 1
            }
            window.removeFirst()
        }
    }
    error("distinct not found")
}

fun part2(input: String) {
    val index = detectDistinct(input, 14)
    println("index = ${index}")
}