package day10

import asResource
import kotlin.math.abs

fun main() {
    val commands = "day10.txt".asResource { it.split("\n") }.getCircleMap()
    println("Q1: ${commands.sumSignalStrength()}")
    println("Q2: \n ${commands.getCRTDraw()}")
}

fun MutableMap<Int, Int>.getCRTDraw() = (0 until 240 step 40)
    .joinToString("\n") { round ->
        (0 until 40).joinToString("") {
            if (abs(this[round + it + 1]!! - it) <= 1) "#" else "."
        }
    }

fun MutableMap<Int, Int>.sumSignalStrength() = this.filter { it.key % 40 == 20 }.map { it.key * it.value }.sum()

fun List<String>.getCircleMap(): MutableMap<Int, Int> {
    var circleNum = 1
    var x = 1
    val circleMap = mutableMapOf(1 to 1)
    this.forEach {
        circleNum += 1
        circleMap[circleNum] = x
        if (it.startsWith("addx")) {
            circleNum += 1
            x += it.split(" ")[1].toInt()
            circleMap[circleNum] = x
        }
    }
    return circleMap
}
