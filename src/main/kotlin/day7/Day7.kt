package day7

import getResourceText

fun main(args: Array<String>) {

    val input = getResourceText("/day7/input")
    val root = Dir(name = "/", parent = null)
    parse(root, input.lines().drop(1).toMutableList())
    root.print()

    part1(root)
    part2(root)
}

fun parse(current: Dir, remainingLines: MutableList<String>) {
    val line = remainingLines.removeFirstOrNull() ?: return
    val split = line.split(" ")
    if (split[1] == "ls") {
        val contents = remainingLines.takeWhile { !it.startsWith("$") }
        val newRemaining = remainingLines.drop(contents.size).toMutableList()
        contents.forEach {
            val (first, second) = it.split(" ")
            if (first == "dir") {
                if (current.dirs.none { it.name == second }) {
                    current.dirs.add(Dir(name = second, parent = current))
                }
            } else {
                if (current.files.none { it.first == second }) {
                    current.files.add(second to first.toInt())
                }
            }
        }
        parse(current = current, remainingLines = newRemaining)
    }
    if (split[1] == "cd") {
        when (val toFolder = split[2]) {
            ".." -> parse(current = current.parent!!, remainingLines)
            else -> {
                val next = if (current.dirs.none { it.name == toFolder }) {
                    val dir = Dir(name = toFolder, parent = current)
                    current.dirs.add(dir)
                    dir
                } else {
                    current.dirs.find { it.name == toFolder }!!
                }
                parse(next, remainingLines)
            }
        }
    }
}

fun part1(root: Dir) {
    val under100k = mutableListOf<Pair<Dir, Int>>()
    sumUnder100k(root, under100k)
    println("Sum of under 100k: ${under100k.sumOf { it.second }}")
}

fun sumUnder100k(dir: Dir, under100k: MutableList<Pair<Dir, Int>>): Int {
    val size = dir.files.sumOf { it.second } + dir.dirs.sumOf { sumUnder100k(it, under100k) }
    if (size < 100_000) {
        under100k.add(dir to size)
    }
    return size
}

fun part2(root: Dir) {
    val dirsAndSize = mutableListOf<Pair<Dir, Int>>()
    val totalSize = sum(root, dirsAndSize)
    val free = 70000000 - totalSize
    val mustFreeUp = 30000000 - free
    val sorted = dirsAndSize.sortedBy { it.second }
    val smallestBigEnough = sorted.find { it.second > mustFreeUp }
    println("smallestBigEnough = ${smallestBigEnough?.second}")
}

fun sum(dir: Dir, dirsAndSize: MutableList<Pair<Dir, Int>>): Int {
    val size = dir.files.sumOf { it.second } + dir.dirs.sumOf { sum(it, dirsAndSize) }
    dirsAndSize.add(dir to size)

    return size
}

data class Dir(
    val name: String,
    val parent: Dir?,
    val dirs: MutableList<Dir> = mutableListOf<Dir>(),
    val files: MutableList<Pair<String, Int>> = mutableListOf<Pair<String, Int>>()
) {
    fun print(prefix: String = "") {
        println("$prefix- $name (dir)")
        files.forEach {
            println("$prefix  - ${it.first} (file, size=${it.second})")
        }
        dirs.forEach { it.print("$prefix  ") }
    }
}