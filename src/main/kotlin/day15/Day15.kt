package day15

import asResource
import kotlin.math.abs

fun main(){
    val sb = parseSB()
    println("Q1: ${sb.countImpossibleLocsInRow(2000000)}")
    println("Q2: ${sb.findUndetectedBeacon(4000000)}")
}

fun List<Pair<Pair<Int, Int>, Pair<Int, Int>>>.countImpossibleLocsInRow(row: Int) =
    this.findImpossibleLocsInRow(row).size - this.map {it.second}.filter { it.second==row }.toSet().size

private fun List<Pair<Pair<Int, Int>, Pair<Int, Int>>>.findImpossibleLocsInRow(row: Int) =
    this.mapNotNull { it.first.findHorizontalRange(it.second,row) }.flatten().toSet()

private fun List<Pair<Pair<Int, Int>, Pair<Int, Int>>>.findImpossibleLocsInRowWithLimit(row: Int, min: Int, max: Int) =
    this.mapNotNull { it.first.findHorizontalRange(it.second,row) }.flatten().filter { it in min..max }.toSet()

fun List<Pair<Pair<Int, Int>, Pair<Int, Int>>>.findUndetectedBeacon(maximum: Int) = (0..maximum)
    .firstOrNull {this.findImpossibleLocsInRowWithLimit(it, 0, maximum).size<=maximum}?.let {row->
    val cols = (0..maximum).toMutableList()
    val impossibleLocs = this.findImpossibleLocsInRow(row)
    cols.removeAll { impossibleLocs.contains(it) }
    Pair(cols[0],row)
}

private fun Pair<Int, Int>.getDistanceFrom(that: Pair<Int,Int>) = abs(this.first-that.first)+ abs(this.second-that.second)

fun Pair<Int, Int>.findHorizontalRange(beacon: Pair<Int, Int>, row: Int): IntRange? {
    val horizontalWidth = this.getDistanceFrom(beacon)-abs(this.second -row)
    return (this.first-horizontalWidth ..this.first+horizontalWidth).takeIf { it.first <= it.last }
}

fun parseSB() = "day15.txt".asResource {
    it.split("\n").map {
        Pair(
            Pair(
                it.substringAfter("x=").substringBefore(",").toInt(),
                it.substringAfter("y=").substringBefore(":").toInt(),
            ),
            Pair(
                it.substringAfterLast("x=").substringBefore(",").toInt(),
                it.substringAfterLast("=").toInt(),
            )
        )
    }
}