package day08

import asResource
fun main() {
    val grid = processFile()
    println("Q1: ${countAllVisibleTreesInGrid(grid)}")
    println("Q2: ${findLargestScenicScore(grid)}")
}

fun countAllVisibleTreesInGrid(grid: List<List<Int>>) = (grid.indices)
    .flatMap { row -> grid[0].indices.map { col -> Pair(row, col) } }
    .count { isVisible(grid, it.first, it.second) }

fun findLargestScenicScore(grid: List<List<Int>>) = (grid.indices)
    .flatMap { row -> grid[0].indices.map { col -> Pair(row, col) } }
    .maxOf { countScenicScore(grid, it.first, it.second) }

val isVisible: (List<List<Int>>, Int, Int) -> Boolean = { grid: List<List<Int>>, row: Int, col: Int ->
    row == 0 || row == grid.size - 1 || col == 0 || col == grid[0].size - 1 ||
        grid[row].subList(0, col).max() < grid[row][col] ||
        grid[row].subList(col + 1, grid[0].size).max() < grid[row][col] ||
        grid.map { it[col] }.subList(0, row).max() < grid[row][col] ||
        grid.map { it[col] }.subList(row + 1, grid.size).max() < grid[row][col]
}

val countScenicScore: (List<List<Int>>, Int, Int) -> Int = { grid: List<List<Int>>, row: Int, col: Int ->
    fun findBlock(list: List<Int>, num: Int) = ((list.indices).firstOrNull { num <= list[it] } ?: (list.size - 1)) + 1
    findBlock(grid[row].subList(0, col).reversed(), grid[row][col]) *
        findBlock(grid[row].subList(col + 1, grid[0].size), grid[row][col]) *
        findBlock(grid.map { it[col] }.subList(0, row).reversed(), grid[row][col]) *
        findBlock(grid.map { it[col] }.subList(row + 1, grid.size), grid[row][col])
}

fun processFile() = "day08.txt".asResource {
    it.split("\n").map { it.map { it.toString().toInt() } }
}
