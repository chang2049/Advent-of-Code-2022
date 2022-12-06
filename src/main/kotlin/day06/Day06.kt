package day06

import asResource

fun main() {
    val str = "day06.txt".asResource { it }
    println("Q1: ${findFirstNUniqueString(str, 4)}")
    println("Q2: ${findFirstNUniqueString(str, 14)}")
}

fun findFirstNUniqueString(str: String, n: Int): Int? {
    val queue = ArrayDeque(str.substring(0, n - 1).toCharArray().toList())
    ((n - 1)until str.length).forEach {
        queue.addLast(str[it])
        if (queue.toSet().size == n) return it + 1
        queue.removeFirst()
    }
    return null
}
