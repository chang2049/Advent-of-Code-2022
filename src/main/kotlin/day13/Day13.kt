package day13

import asResource
import java.lang.Integer.max

fun main() {
    val packetPairs = "day13.txt"
        .asResource {
            it.split("\n\n")
                .map { Pair(parse(it.split("\n")[0]), parse(it.split("\n")[1])) }
        }
    println("Q1: ${sumIndexOfRightOrderedPackets(packetPairs)}")
    println("Q2: ${multipleDecoderKey(packetPairs, parse("[[2]]"), parse("[[6]]"))}")
}

fun sumIndexOfRightOrderedPackets(packetPairs: List<Pair<ArrayDeque<Any>, ArrayDeque<Any>>>) =
    packetPairs.mapIndexed { index, packet -> Pair(index, packet.first.compareTo(packet.second)) }
        .filter { it.second < 0 }.sumOf { it.first + 1 }

fun multipleDecoderKey(packets: List<Pair<ArrayDeque<Any>, ArrayDeque<Any>>>, a: ArrayDeque<Any>, b: ArrayDeque<Any>) =
    (packets.flatMap { it.toList() } + listOf(a, b)).sortedWith(ArrayDeque<*>::compareTo)
        .let { (it.indexOf(a) + 1) * (it.indexOf(b) + 1) }

fun ArrayDeque<*>.compareTo(other: ArrayDeque<*>): Int {
    (0..max(this.size - 1, other.size - 1)).forEach {
        if (other.size < it + 1) return 1
        if (this.size < it + 1) return -1
        val result = compare(this[it]!!, other[it]!!)
        if (result != 0) return result
    }
    return 0
}

private fun compare(a: Any, b: Any): Int {
    return if (a is ArrayDeque<*> && b is ArrayDeque<*>) {
        a.compareTo(b)
    } else if (a is Int && b is Int) {
        a - b
    } else if (a is ArrayDeque<*> && b is Int) {
        val new = ArrayDeque<Any>()
        new.addLast(b)
        a.compareTo(new)
    } else if (a is Int && b is ArrayDeque<*>) {
        val new = ArrayDeque<Any>()
        new.addLast(a)
        new.compareTo(b)
    } else {
        error("not supported ")
    }
}

fun parse(str: String): ArrayDeque<Any> {
    val stack = ArrayDeque<Any>()
    val stackRecorder = ArrayDeque<ArrayDeque<Any>>()
    var currentStack = stack
    var currentStr = ""
    for (char in str.toCharArray()) {
        when (char) {
            '[' -> {
                var newStack = ArrayDeque<Any>()
                stackRecorder.addLast(currentStack)
                currentStack.addLast(newStack)
                currentStack = newStack
            }
            ']' -> {
                if (currentStr.isNotEmpty()) {
                    currentStack.addLast(currentStr.toInt())
                    currentStr = ""
                }
                currentStack = stackRecorder.removeLast()
            }
            ',' -> {
                if (currentStr.isNotEmpty()) {
                    currentStack.addLast(currentStr.toInt())
                    currentStr = ""
                }
            }
            else -> currentStr += char
        }
    }
    return stack
}
