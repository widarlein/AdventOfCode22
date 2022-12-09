package day9

import getResourceText
import kotlin.math.*

fun main(args: Array<String>) {

    val input = getResourceText("/day9/input")

    part1(input)
    part2(input)
}

fun part1(input: String) {
    var head = 0 to 0
    var tail = 0 to 0
    val tailPositions = mutableSetOf(tail)

    input.lines().forEach {
        val (direction, stepsString) = it.split(" ")
        val steps = stepsString.toInt()
        val step = when (direction) {
            "R" -> 1 to 0
            "L" -> -1 to 0
            "U" -> 0 to 1
            "D" -> 0 to -1
            else -> error("Not valid direction: $direction")
        }
        repeat(steps) {
            head += step
            if (head isNotTouching tail) {
                val diff = head - tail
                val tailStep = if (diff.first.absoluteValue > diff.second.absoluteValue) {
                    val normalizedish = diff.first / abs(diff.first)
                    diff.first - normalizedish to diff.second
                } else {
                    val normalizedish = diff.second / abs(diff.second)
                    diff.first to diff.second - normalizedish
                }
                tail += tailStep
                tailPositions.add(tail)
            }

        }
    }

    println("Unique tail positions: ${tailPositions.size}")
}

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = this.first + other.first to this.second + other.second
operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>) = this.first - other.first to this.second - other.second

infix fun Pair<Int, Int>.isNotTouching(other: Pair<Int, Int>) = sqrt(
    (other.first - this.first.toFloat()).pow(2) + (other.second - this.second.toFloat()).pow(2)
) > sqrt(2f)

fun part2(input: String) {
    val knots = Array(10) { 0 to 0 }
    val tailPositions = mutableSetOf(knots.last())

    input.lines().forEach {
        val (direction, stepsString) = it.split(" ")
        val steps = stepsString.toInt()
        val step = when (direction) {
            "R" -> 1 to 0
            "L" -> -1 to 0
            "U" -> 0 to 1
            "D" -> 0 to -1
            else -> error("Not valid direction: $direction")
        }
        repeat(steps) {
//            following.print()
            knots[0] += step
            for (i in 1..knots.lastIndex) {
                knots[i] = newPosition(knots[i], knots[i - 1])
            }
            tailPositions.add(knots.last())

        }
//        following.print()
    }

    println("Unique tail positions: ${tailPositions.size}")
}

fun newPosition(knot: Pair<Int, Int>, precedes: Pair<Int, Int>): Pair<Int, Int> {
    return if (knot isNotTouching precedes) {
        val diff = precedes - knot
        val stepX = if (diff.first.absoluteValue > 1) {
            val normalizedish = diff.first / abs(diff.first)
            diff.first - normalizedish
        } else diff.first

        val stepY = if(diff.second.absoluteValue > 1) {
            val normalizedish = diff.second / abs(diff.second)
            diff.second - normalizedish
        } else diff.second
        knot + (stepX to stepY)
    } else {
        knot
    }
}

private fun Array<Pair<Int, Int>>.print() {
    val xMax = max(this.maxBy { it.first }.first, 14)
    val xMin = min(this.minBy { it.first }.first, -11)
    val yMax = max(this.maxBy { it.second }.second, 15)
    val yMin = min(this.minBy { it.second }.second, -5)

    for (i in yMax downTo yMin) {
        val row = this.mapIndexed {index, pair -> index to pair }.filter { it.second.second == i }
        for (j in xMin .. xMax) {
            val knotAndName = row.find { it.second.first == j }
            if (knotAndName != null) {
                print(knotAndName.first)
            } else {
                print(".")
            }
        }
        print("\n")
    }
    print("\n\n")
}

