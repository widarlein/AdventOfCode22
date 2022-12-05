package day5

import getResourceText

fun main(args: Array<String>) {

    val input = getResourceText("/day5/input")
    val (config, instructions) = input.split("\n\n")
    part1(parseConfig(config), parseInstructions(instructions))
    part2(parseConfig(config), parseInstructions(instructions))

}

fun parseConfig(s: String): Array<MutableList<Char>> {
    val reversed = s.lines().dropLast(1).asReversed()
    val crates = Array<MutableList<Char>>((reversed.first().length + 1) / 4) {
        mutableListOf()
    }

    reversed.forEach {
        for (i in it.indices step 4) {
            if (it[i + 1] != ' ') crates[i / 4].add(it[i + 1])
        }
    }
    return crates
}

fun parseInstructions(s: String) = s.lines().map {
    val r = """move (\d+) from (\d+) to (\d+)""".toRegex()
    val res = r.matchEntire(it)

    val (move, from, to) = res!!.destructured
    Triple(
        move.toInt(), from.toInt() - 1, to.toInt() - 1
    )
}

fun part1(crates: Array<MutableList<Char>>, instructions: List<Triple<Int, Int, Int>>) {
    instructions.forEach { inst ->
        repeat(inst.first) {
            val crate = crates[inst.second].removeLast()
            crates[inst.third].add(crate)
        }
    }

    println("crates = ${crates.map { it.last() }.joinToString(separator = "")}")
}

fun part2(crates: Array<MutableList<Char>>, instructions: List<Triple<Int, Int, Int>>) {
    instructions.forEach { inst ->
        val gripCrates = crates[inst.second].takeLast(inst.first)
        crates[inst.second] = crates[inst.second].dropLast(inst.first).toMutableList()
        crates[inst.third].addAll(gripCrates)
    }

    println("crates = ${crates.map { it.last() }.joinToString(separator = "")}")
}