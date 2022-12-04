package day2

import getResourceText

fun main(args: Array<String>) {

    val input = getResourceText("/day2/input")
    part1(input)
    part2(input)

}

fun part1(input: String) {
    val sum = input.lines().sumOf {
        val (theirs, mine) = it.split(" ")
        mine.shapeScore() + (mine beats theirs)
    }

    println("sum = ${sum}")
}
fun String.shapeScore(): Int = when(this) {
    "X" -> 1
    "Y" -> 2
    "Z" -> 3
    else -> error("$this is NOT A SHAPE")
}

infix fun String.beats(s: String): Int = when(this) {
    "X" -> when(s) {
        "A" -> 3
        "B" -> 0
        "C" -> 6
        else -> error("Not a shape")
    }
    "Y" -> when(s) {
        "A" -> 6
        "B" -> 3
        "C" -> 0
        else -> error("Not a shape")
    }
    "Z" -> when(s) {
        "A" -> 0
        "B" -> 6
        "C" -> 3
        else -> error("Not a shape")
    }
    else -> error("Not a shape")
}


fun part2(input: String) {
    val sum = input.lines().sumOf {
        val (theirs, tactic) = it.split(" ")
        val mine = theirs.shapeToDo(tactic)
        mine.shapeScore2() + (mine beats2 theirs)
    }

    println("part2 sum = ${sum}")

}

fun String.shapeScore2(): Int = when(this) {
    "A" -> 1
    "B" -> 2
    "C" -> 3
    else -> error("$this is NOT A SHAPE")
}

infix fun String.beats2(s: String): Int = when(this) {
    "A" -> when(s) {
        "A" -> 3
        "B" -> 0
        "C" -> 6
        else -> error("Not a shape")
    }
    "B" -> when(s) {
        "A" -> 6
        "B" -> 3
        "C" -> 0
        else -> error("Not a shape")
    }
    "C" -> when(s) {
        "A" -> 0
        "B" -> 6
        "C" -> 3
        else -> error("Not a shape")
    }
    else -> error("Not a shape")
}

infix fun String.shapeToDo(s: String) = when(this) {
    "A" -> when(s) {
        "X" -> "C"
        "Y" -> "A"
        "Z" -> "B"
        else -> error("$s is Not a tactic")
    }
    "B" -> when(s) {
        "X" -> "A"
        "Y" -> "B"
        "Z" -> "C"
        else -> error("$s is Not a tactic")
    }
    "C" -> when(s) {
        "X" -> "B"
        "Y" -> "C"
        "Z" -> "A"
        else -> error("$s is Not a tactic")
    }
    else -> error("Not a shape")
}