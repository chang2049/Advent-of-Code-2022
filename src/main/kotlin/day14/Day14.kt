package day14

import asResource
import kotlin.math.max
import kotlin.math.min
fun main() {
    val rocks = loadAllSolidCoords()
    println("Q1: ${countNoneFlowedSand(rocks, false)}")
    println("Q2: ${countNoneFlowedSand(rocks,true)}")
}

fun countNoneFlowedSand(rocks: Set<Pair<Int, Int>>, withBottom: Boolean): Int {
    val sandSet = mutableSetOf<Pair<Int, Int>>()
    val bottom = rocks.maxOf { it.second }
    var currentSand = Pair(500, 0)
    while (true) {
        var newSand = currentSand.down()
        if (rocks.contains(newSand) || sandSet.contains(newSand) || newSand.second == bottom + 2) {
            newSand = currentSand.downLeft()
        }
        if (rocks.contains(newSand) || sandSet.contains(newSand) || newSand.second == bottom + 2) {
            newSand = currentSand.downRight()
        }
        if (rocks.contains(newSand) || sandSet.contains(newSand) || newSand.second == bottom + 2) {
            sandSet.add(currentSand)
            if (currentSand == Pair(500, 0) && withBottom) break
            currentSand = Pair(500, 0)
        } else {
            currentSand = newSand
        }
        if (!withBottom && currentSand.second > bottom) break
    }
    return sandSet.size
}

fun Pair<Int, Int>.down() = Pair(this.first, this.second + 1)
fun Pair<Int, Int>.downLeft() = Pair(this.first - 1, this.second + 1)
fun Pair<Int, Int>.downRight() = Pair(this.first + 1, this.second + 1)

fun loadAllSolidCoords() = "day14.txt".asResource {
    it.split("\n").map {
        it.split(" -> ")
            .map { Pair(it.split(",")[0].toInt(), it.split(",")[1].toInt()) }
    }
}
    .flatMap { path ->
        (0 until path.size - 1).flatMap {
            (min(path[it].first, path[it + 1].first)..max(path[it].first, path[it + 1].first)).flatMap { x ->
                (min(path[it].second, path[it + 1].second)..max(path[it].second, path[it + 1].second)).map { y -> Pair(x, y) }
            }
        }
    }.toSet()
