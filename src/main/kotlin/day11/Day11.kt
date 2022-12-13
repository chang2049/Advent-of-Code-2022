package day11

import asResource

fun main() {
    println("Q1: ${processFile().inspect(20,3).map { it.inspectCount }
        .sortedDescending().take(2).reduce { acc, i -> acc * i }}")
    println("Q2: ${processFile().inspect(10000,1).map { it.inspectCount }
        .sortedDescending().take(2).reduce { acc, i -> acc * i }}")
}

fun List<Monkey>.inspect(rounds: Int, division: Long): List<Monkey> {
    val mul = this.map { it.divisible }.toSet().reduce { acc, l -> acc * l } * division
    repeat(rounds) { this.forEach { it.inspect(this, division, mul) } }
    return this
}

class Monkey(
    var inspectCount: Long = 0,
    val id: Long,
    var items: MutableList<Long>,
    val operation: (Long) -> Long,
    val divisible: Long,
    private val toMonkeyNumWhenTrue: Long,
    private val toMonkeyNumWhenFalse: Long
) {
    fun inspect(monkeys: List<Monkey>, levelDivision: Long, m: Long) {
        for (item in items) {
            inspectCount += 1
            var worryLevel = operation(item) / levelDivision
            if (m != null) worryLevel %= m
            if (worryLevel % divisible == 0L) {
                monkeys.first { it.id == toMonkeyNumWhenTrue }.items.add(worryLevel)
            } else {
                monkeys.first { it.id == toMonkeyNumWhenFalse }.items.add(worryLevel)
            }
        }
        items = mutableListOf()
    }
}

fun String.toMonkey(): Monkey {
    val lines = this.split("\n")
    val id = lines[0].split(" ")[1].split(":")[0].toLong()
    val items = lines[1].split(": ")[1].split(", ").map { it.toLong() }.toMutableList()
    val operation: (Long) -> Long = { number ->
        val op = lines[2].split("= ")[1].split(" ")[1]
        val left = lines[2].split("= ")[1].split(" ")[0].let { if (it == "old") number else it.toLong() }
        val right = lines[2].split("= ")[1].split(" ")[2].let { if (it == "old") number else it.toLong() }
        when (op) {
            "+" -> left + right
            "*" -> left * right
            "/" -> left / right
            else -> error("")
        }
    }
    val divisible = lines[3].split("by ")[1].toLong()
    val toTrue = lines[4].split("monkey ")[1].toLong()
    val toFalse = lines[5].split("monkey ")[1].toLong()
    return Monkey(0, id, items, operation, divisible, toTrue, toFalse)
}

fun processFile() = "day11.txt".asResource { it.split("\n\n").map { it.toMonkey() } }
