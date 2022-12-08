package day8

import getResourceText

fun main(args: Array<String>) {

    val input = getResourceText("/day8/input")
    val matrix = input.lines().map { it.toCharArray().map { it.digitToInt() } }

    part1(matrix)
    part2(matrix)
}

fun part1(matrix: List<List<Int>>) {
    val edgeCount = matrix.first().size * 2 + (matrix.size - 2) * 2
    var insideVisible = 0
    for (i in 1 until matrix.lastIndex) {
        for (j in 1 until matrix[i].lastIndex) {
            val currentHeight = matrix[i][j]
            val lefts = matrix[i].subList(0, j)
            if (currentHeight isTallerThan lefts){
                insideVisible++
                continue
            }
            val rights = matrix[i].subList(j+1, matrix[i].size)
            if (currentHeight isTallerThan rights){
                insideVisible++
                continue
            }

            val ups = matrix.filterIndexed {index, _ -> index < i }.map { it[j] }
            if (currentHeight isTallerThan ups){
                insideVisible++
                continue
            }

            val downs = matrix.filterIndexed {index, _ -> index > i }.map { it[j] }
            if (currentHeight isTallerThan downs){
                insideVisible++
            }
        }
    }

    println("Visible trees: ${edgeCount + insideVisible}")
}

infix fun Int.isTallerThan(others: List<Int>) = others.all { it < this }

fun part2(matrix: List<List<Int>>) {
    val scores = mutableListOf<Int>()
    for (i in 1 until matrix.lastIndex) {
        for (j in 1 until matrix[i].lastIndex) {
            val currentHeight = matrix[i][j]

            val lefts = matrix[i].subList(0, j)
            val visibleLeft = lefts
                .takeLastWhile { it < currentHeight }
            val leftCount = if (visibleLeft == lefts) visibleLeft.size else visibleLeft.size + 1

            val rights = matrix[i].subList(j+1, matrix[i].size)
            val visibleRight = rights
                .takeWhile { it < currentHeight }
            val rightCount = if (visibleRight == rights) visibleRight.size else visibleRight.size + 1


            val ups = matrix.filterIndexed { index, _ -> index < i }.map { it[j] }
            val visibleUp = ups
                .takeLastWhile { it < currentHeight }
            val upCount = if (visibleUp == ups) visibleUp.size else visibleUp.size + 1


            val downs = matrix.filterIndexed { index, _ -> index > i }.map { it[j] }
            val visibleDown = downs
                .takeWhile { it < currentHeight }
            val downCount = if (visibleDown == downs) visibleDown.size else visibleDown.size + 1


            val scenicScore = leftCount *
                    rightCount *
                    upCount *
                    downCount
            scores.add(scenicScore)
        }
    }
    println("highest = ${scores.max()}")
}