package math.approximation

import kotlin.math.roundToInt
import kotlin.system.exitProcess
import kotlin.math.sqrt as Ksqrt

fun sqrt(x: Double, approximation: Double, seq: Int = 1, eps: Double = 0.000_000_1): Pair<Int, Double> {
    val nextApproximation = (approximation + x / approximation) / 2
    if (Math.abs(approximation - nextApproximation) <= eps) {
        return seq to nextApproximation
    } else {
        return sqrt(x, nextApproximation, seq + 1)
    }
}

fun main(args: Array<String>) {
    when (args.size) {
        0 -> println("At least 1 argument is expected")
            .also { exitProcess(1) }
        else -> {
            args.forEach { arg ->
                val number = arg.toDouble()
                val (loops, approx) = sqrt(number, smallerQuadrant(number))
                println("sqrt($number)=$approx after $loops iterations")
                println("sqrt($number)=${Ksqrt(number)}")
            }
        }
    }
}

fun smallerQuadrant(i: Double): Double =
    (0.rangeTo(i.roundToInt())
        .findLast { value -> value > 0 && value * value <= i } ?: 1)
        .also { println("Initial approximation=$it") }
        .toDouble()

//
//fun max(a: Int, b: Int) = if (a > b) a else b
//
//val m = max(2, 3)


val length = "".also { s -> s.length }