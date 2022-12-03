package day03

import asResource
fun main(){
    val items = processFile()
    println("Q1: ${sumPriorityValues(items)}")
    println("Q2: ${sumBadgeItemValues(items)}")
}

fun sumPriorityValues(items: List<String>) = items
    .map { Pair(it.substring(0,it.length/2), it.substring(it.length/2,it.length))
        .findDuplicatedChar().getValue() }.sum()

fun sumBadgeItemValues(items: List<String>) = (items.indices step 3).map {
    items[it].toSet().intersect(items[it+1].toSet()).intersect(items[it+2].toSet()).toList()[0].getValue()
}.sum()

fun Pair<String,String>.findDuplicatedChar() = this.first.toSet().first{this.second.toSet().contains(it)}

fun Char.getValue() = if (this.isUpperCase()) this-'A'+27 else this-'a'+1

fun processFile() = "day03.txt".asResource { it.split("\n") }