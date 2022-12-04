package day04

import asResource

fun main() {
    val pairs = processFile()
    println("Q1: ${countPairs(pairs, isFullyContained)}")
    println("Q2: ${countPairs(pairs, isOverlapped)}")
}

fun countPairs(
    pairs: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>,
    validate: (Pair<Int, Int>, Pair<Int, Int>) -> Boolean
) =
    pairs.count { validate(it.first, it.second) }

val isFullyContained: (Pair<Int, Int>, Pair<Int, Int>) -> Boolean = {
        sectionA: Pair<Int, Int>, sectionB: Pair<Int, Int> ->
    (sectionA.first <= sectionB.first && sectionA.second >= sectionB.second) ||
        (sectionA.first >= sectionB.first && sectionA.second <= sectionB.second)
}

val isOverlapped: (Pair<Int, Int>, Pair<Int, Int>) -> Boolean = {
        sectionA: Pair<Int, Int>, sectionB: Pair<Int, Int> ->
    (sectionA.first <= sectionB.second && sectionA.second >= sectionB.first) ||
        (sectionB.first <= sectionA.second && sectionB.second >= sectionA.first)
}

fun processFile() = "day04.txt".asResource {
    it.split("\n").map {
        val sections = it.split(",")
        Pair(sections[0].parseSection(), sections[1].parseSection())
    }
}

fun String.parseSection() = Pair(this.split("-")[0].toInt(), this.split("-")[1].toInt())
