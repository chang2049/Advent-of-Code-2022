package day12

import asResource

fun main() {
    val (map, S, E) = loadMap()
    println("Q1: ${bfs(map,S, E)}")
    println(("Q2: ${findTheBestStart(map,E)}"))
}

fun findTheBestStart(grid: List<List<Char>>, end: Int): Int? {
    val startPoints = grid.indices.filter { grid[it].contains('S') or grid[it].contains('a') }
        .flatMap { row -> grid[row].indices.filter { grid[row][it] == 'S' || grid[row][it] == 'a' }.map { row * grid[0].size + it } }
    return startPoints.minOfOrNull { bfs(grid, it, end) ?: Int.MAX_VALUE }
}

fun bfs(grid: List<List<Char>>, start: Int, end: Int): Int? {
    val visited = mutableSetOf<Int>()
    val visitedChars = mutableSetOf<Char>()
    val queue = ArrayDeque(listOf(Pair(start, 0)))
    while (queue.isNotEmpty()) {
        val item = queue.removeFirst()
        if (!visited.contains(item.first)) {
            if (item.first == end) return item.second
            visited.add(item.first)
            val x = item.first / grid[0].size
            val y = item.first % grid[0].size
            visitedChars.add(grid[x][y])
            for (loc in listOf(Pair(x - 1, y), Pair(x + 1, y), Pair(x, y - 1), Pair(x, y + 1))) {
                if (loc.first in 0 until grid.size && loc.second in 0 until grid[0].size &&
                    !visited.contains(grid[0].size*loc.first+loc.second) &&
                    grid[loc.first][loc.second].getHeight() - grid[x][y].getHeight() <= 1
                ) {
                    queue.addLast(Pair(loc.first * grid[0].size + loc.second, item.second + 1))
                }
            }
        }
    }
    return null
}

fun loadMap(): Triple<List<List<Char>>, Int, Int> {
    val map = "day12.txt".asResource { it.split("\n") }.map { it.map { it } }
    val S = map.indices.first { map[it].contains('S') }.let { it * map[0].size + map[it].indexOf('S') }
    val E = map.indices.first { map[it].contains('E') }.let { it * map[0].size + map[it].indexOf('E') }
    return Triple(map, S, E)
}

fun Char.getHeight() = if (this.isUpperCase()) {
    when (this) {
        'E' -> 'z'
        'S' -> 'a'
        else -> error("")
    }
} else {
    this
}
