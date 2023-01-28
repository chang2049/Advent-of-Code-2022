package day17

import asResource

fun main() {
    val instructs = "day17.txt".asResource { it }


}

fun String.findHeight(rockNum: Long): Long {
    val currentRocks = mutableSetOf<Pair<Long,Long>>()
    var currentHeight = -1L
    var instructCount = 0
    for(i in 0 until rockNum ){
        var newRock = Rock.originalRocks[(i%Rock.originalRocks.size).toInt()].addHeight(currentHeight+ 1L)
        var prior = newRock
        while (!newRock.locations.any { currentRocks.contains(it) || it.second<0}){
            when(this[instructCount++%this.length]){
                '<' -> newRock = if(newRock.goLeft().locations.any { currentRocks.contains(it) || it.first<0 }) newRock else newRock.goLeft()
                '>' -> newRock = if(newRock.goRight().locations.any{ currentRocks.contains(it) || it.first>6 }) newRock else newRock.goRight()
            }
            prior = newRock
            newRock = newRock.goDown()
        }
        currentRocks.addAll(prior.locations)
        currentHeight = kotlin.math.max(currentHeight, prior.locations.maxOf { it.second })
    }
    return currentHeight+1
}

data class Rock(val locations: List<Pair<Long, Long>>){
    fun goLeft() = Rock(locations.map { Pair(it.first-1, it.second)})
    fun goRight() = Rock(locations.map { Pair(it.first+1, it.second)})
    fun goDown() = Rock(locations.map { Pair(it.first,it.second-1) })
    fun addHeight(height: Long) = Rock(locations.map { Pair(it.first, it.second+height) })

    companion object{
        val originalRocks = listOf(
            Rock(listOf(Pair(2,3), Pair(3,3), Pair(4,3), Pair(5,3))),
            Rock(listOf(Pair(3,3),Pair(2,4), Pair(3,4), Pair(4,4), Pair(3,5))),
            Rock(listOf(Pair(2,3),Pair(3,3),Pair(4,3), Pair(4,4), Pair(4,5))),
            Rock(listOf(Pair(2,3), Pair(2,4), Pair(2,5), Pair(2,6))),
            Rock(listOf(Pair(2,3), Pair(3,3), Pair(2,4), Pair(3,4)))
        )
    }
}