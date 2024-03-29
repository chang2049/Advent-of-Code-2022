package day15

import asResource
import java.io.File
import kotlin.math.abs


val input = "day15.txt".asResource {it.split("\n")}

data class Coordinates(val x: Int, val y: Int)
data class Sensor(val coordinates: Coordinates, val radius: Int)

data class Beacon(val coordinates: Coordinates)

//also the radius of a "diamond"
fun Coordinates.manhattanDistance(other: Coordinates) =
    abs(x - other.x) + abs(y - other.y)

//also the radius of a "diamond"
fun String.extractCoordinates() =
    "(-?\\d+)".toRegex().findAll(this).map { it.groupValues[1] }.map { it.toInt() }.toList()

// list of sensors and set of points
fun parseInput(input: () -> List<String>) =
    input().fold(Pair(listOf(), setOf())) { acc: Pair<List<Sensor>, Set<Beacon>>, s: String ->
        val parsedCoordinates = s.extractCoordinates()
        val (sensorCoords, beaconCoords) = Pair(
            Coordinates(
                parsedCoordinates[0],
                parsedCoordinates[1]
            ), Coordinates(parsedCoordinates[2], parsedCoordinates[3])
        )
        Pair(
            acc.first + Sensor(sensorCoords, sensorCoords.manhattanDistance(beaconCoords)),
            acc.second + Beacon(beaconCoords)
        )
    }

fun day151(): Int {
    val (sensors, beacons) = parseInput { input }

    val rowToCheck = 2000000
    val positionsWithoutBeacon = mutableSetOf<Coordinates>()
    // filter all sensors that are in range of the row to check
    // and check their range against the beacons in range
    sensors.filter { it.radius >= it.coordinates.y - rowToCheck }.forEach { sensor ->
        val distanceToCenter = abs(sensor.coordinates.y - rowToCheck)
        val leftmostPointForRow = sensor.coordinates.x - sensor.radius + distanceToCenter
        val rightmostPointForRow = sensor.coordinates.x + sensor.radius - distanceToCenter
        // iterate beacons and check if any are in range
        (leftmostPointForRow..rightmostPointForRow).forEach { x ->
            val toAdd = Coordinates(x, rowToCheck)
            if (beacons.none { it.coordinates.x == x && it.coordinates.y == rowToCheck }) {
                positionsWithoutBeacon.add(toAdd)
            }
        }
    }

    return positionsWithoutBeacon.count()
}

fun day152() {
    val (sensors) = parseInput { input }
    val multiply = 4_000_000
    val maxXY = 4_000_000

    for (sensor in sensors) {
        // start at the top
        // calculate leftmost and rightmost X points and check those
        // traversing down to the end of the radius
        // take the point just outside the perimeter edge
        val topY = sensor.coordinates.y - sensor.radius - 1
        val bottomY = sensor.coordinates.y + sensor.radius + 1
        for (y in topY..bottomY) {
            val distanceToCenter = abs(sensor.coordinates.y - y)
            val leftX = sensor.coordinates.x - sensor.radius - 1 + distanceToCenter
            val rightX = sensor.coordinates.x + sensor.radius + 1 - distanceToCenter
            val leftPoint = Coordinates(x = leftX, y = y)
            val rightPoint = Coordinates(x = rightX, y = y)
            if (leftX < 0 || leftX > maxXY || rightX < 0 || rightX > maxXY) {
                continue
            }
            if (y < 0 || y > maxXY) {
                continue
            }
            if (sensors.none {
                    it.radius >= it.coordinates.manhattanDistance(leftPoint)
                }) {
                println(leftPoint)
                println((leftPoint.x.toBigInteger() * multiply.toBigInteger()) + leftPoint.y.toBigInteger())
                return
            }
            if (sensors.none {
                    it.radius >= it.coordinates.manhattanDistance(rightPoint)
                }) {
                println(rightPoint)
                println((rightPoint.x.toBigInteger() * multiply.toBigInteger()) + rightPoint.y.toBigInteger())
                return
            }
        }
    }
}

fun main() {
    println(day151())
    day152()
}