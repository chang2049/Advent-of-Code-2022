package day01

import asResource

fun main() {
    val sortedCalories = sortCalories(processFile())
    println("Q1: ${sortedCalories.max()}")
    println("Q2: ${sortedCalories.subList(0,3).sum()}")
}

fun sortCalories(calList: List<List<Int>>) =
    calList.map { it.sum() }.sortedDescending()

fun processFile() = "day01.txt".asResource {
    it.split("\n\n").map { numlist -> numlist.split("\n").map { it.toInt() } }
}
