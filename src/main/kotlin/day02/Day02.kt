package day02

import asResource

fun main() {
    val scoreQ1 = computeTournamentByShape(processFileQ1())
    println("Q1: $scoreQ1")
    val scoreQ2 = computeTournamentByResult(processFileQ2())
    println("Q2: $scoreQ2")
}

fun computeTournamentByShape(strategy: List<List<Shape>>) = strategy.map { it[1].play(it[0]) }.sum()

fun computeTournamentByResult(strategy: List<Pair<Shape, RoundOutcome>>) = strategy
    .map { it.first.backComputeShapeByResult(it.second).score + it.second.score }.sum()

fun processFileQ1() = "day02.txt".asResource {
    it.split("\n").map { it.split(" ").map { it.toShape() } }
}

fun processFileQ2() = "day02.txt".asResource {
    it.split("\n").map { Pair(it.split(" ")[0].toShape(), it.split(" ")[1].toRoundOutcome()) }
}

enum class Shape(val score: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    fun play(rival: Shape): Int =
        when (this) {
            ROCK -> when (rival) {
                ROCK -> RoundOutcome.DRAW
                PAPER -> RoundOutcome.LOST
                SCISSORS -> RoundOutcome.WON
            }
            PAPER -> when (rival) {
                ROCK -> RoundOutcome.WON
                PAPER -> RoundOutcome.DRAW
                SCISSORS -> RoundOutcome.LOST
            }
            SCISSORS -> when (rival) {
                ROCK -> RoundOutcome.LOST
                PAPER -> RoundOutcome.WON
                SCISSORS -> RoundOutcome.DRAW
            }
        }.score + this.score

    fun backComputeShapeByResult(outcome: RoundOutcome): Shape =
        when (outcome) {
            RoundOutcome.DRAW -> this
            RoundOutcome.WON -> when (this) {
                ROCK -> PAPER
                PAPER -> SCISSORS
                SCISSORS -> ROCK
            }
            RoundOutcome.LOST -> when (this) {
                ROCK -> SCISSORS
                PAPER -> ROCK
                SCISSORS -> PAPER
            }
        }

    companion object {
        fun fromString(str: String) =
            when (str) {
                "A", "X" -> ROCK
                "B", "Y" -> PAPER
                "C", "Z" -> SCISSORS
                else -> error("not matched")
            }
    }
}

enum class RoundOutcome(val score: Int) {
    WON(6),
    DRAW(3),
    LOST(0);

    companion object {
        fun fromString(str: String) =
            when (str) {
                "X" -> LOST
                "Y" -> DRAW
                "Z" -> WON
                else -> error("not matched")
            }
    }
}

fun String.toShape() = Shape.fromString(this)
fun String.toRoundOutcome() = RoundOutcome.fromString(this)
