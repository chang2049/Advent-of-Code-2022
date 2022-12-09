package day09

import asResource
import kotlin.math.abs

fun main() {
    val instructs = "day09.txt".asResource { it.split("\n").map { Pair(it.split(" ")[0], it.split(" ")[1].toInt()) } }
    println("Q1: ${countVisitedPlace(instructs,2)}")
    println("Q2: ${countVisitedPlace(instructs, 10)}")
}

fun countVisitedPlace(instructs: List<Pair<String, Int>>, ropeLen: Int): Int {
    val visited = mutableSetOf<Pair<Int, Int>>()
    val rope = MutableList(ropeLen) { Pair(0, 0) }
    instructs.forEach { instruct ->
        (1..instruct.second).forEach {
            rope[0] = rope[0].move(instruct.first)
            (1 until rope.size).forEach { rope[it] = rope[it].follow(rope[it - 1]) }
            visited.add(rope.last())
        }
    }
    return visited.size
}

fun Pair<Int, Int>.follow(head: Pair<Int, Int>) =
    if (abs(this.first - head.first) <= 1 && abs(this.second - head.second) <= 1) {
        this
    } else if ((abs(this.first - head.first) == 2 && abs(this.second - head.second) == 2) ||
        (this.first == head.first || this.second == head.second)
    ) {
        Pair((this.first + head.first) / 2, (this.second + head.second) / 2)
    } else {
        Pair(
            if (abs(head.first - this.first) == 1) head.first else (this.first + head.first) / 2,
            if (abs(head.second - this.second) == 1) head.second else (this.second + head.second) / 2
        )
    }

fun Pair<Int, Int>.move(str: String) = when (str) {
    "L" -> Pair(this.first - 1, this.second)
    "R" -> Pair(this.first + 1, this.second)
    "U" -> Pair(this.first, this.second + 1)
    "D" -> Pair(this.first, this.second - 1)
    else -> error("instruction is not supported")
}
