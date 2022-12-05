package day05

import asResource

fun main() {
    println("Q1: ${executeInstructs(processFile(), moveItemOneByOne)}")
    println("Q2: ${executeInstructs(processFile(), moveItemAtOnce)}")
}

fun executeInstructs(
    data: Pair<List<ArrayDeque<Char>>, List<List<Int>>>,
    machine: (from: ArrayDeque<Char>, to: ArrayDeque<Char>, repeat: Int) -> Unit
): String {
    data.second.forEach {
        val stacks = data.first
        machine(stacks[it[1] - 1], stacks[it[2] - 1], it[0])
    }
    return data.first.map { it.last() }.joinToString("")
}

val moveItemOneByOne: (ArrayDeque<Char>, ArrayDeque<Char>, Int) -> Unit = {
        from: ArrayDeque<Char>, to: ArrayDeque<Char>, repeat: Int ->
    (1..repeat).forEach { _ ->
        val item = from.removeLast()
        to.addLast(item)
    }
}

val moveItemAtOnce: (ArrayDeque<Char>, ArrayDeque<Char>, Int) -> Unit = {
        from: ArrayDeque<Char>, to: ArrayDeque<Char>, repeat: Int ->
    val items = from.subList(from.size - repeat, from.size).toList()
    (1..repeat).forEach { _ -> from.removeLast() }
    to.addAll(items)
}

fun processFile() = "day05.txt".asResource { it ->
    val crates = it.split("\n\n")[0].split("\n")
    val instructions = it.split("\n\n")[1].split("\n").map {
        val words = it.split(" ")
        listOf(words[1].toInt(), words[3].toInt(), words[5].toInt())
    }
    val stackCount = crates.last().split(" ").let { it[it.size - 2].toInt() }
    val stacks = (1..stackCount).map { ArrayDeque<Char>() }
    crates.subList(0, crates.size - 1).forEach { str ->
        (0..str.length / 4).forEach { if (str[it * 4 + 1] != ' ') stacks[it].addFirst(str[it * 4 + 1]) }
    }
    return@asResource Pair(stacks, instructions)
}
