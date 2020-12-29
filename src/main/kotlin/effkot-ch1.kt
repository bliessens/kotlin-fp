package effective.kotlin.chapter1

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread
import kotlin.properties.Delegates

var names by Delegates.observable(
    listOf<String>(),
    { kp, old, new -> println("Value $old in ${kp.name} was replaced by $new") })


suspend fun main(args: Array<String>) {
//    names += "Benoit"
//    names += "Bie"

    println(isStringType<String>())
    println(compute<String>())

}

val l = listOf(1, 2, 3)
suspend fun coroutineWork() {
    var num = 0
    coroutineScope {
        for (i in 1..1000) {
            launch {
                delay(10)
                num += 1
            }
        }
    }
    println(num)
}

fun threadWork() {
    var num = 0
    thread {
        for (i in 1..1000) {
            Thread.sleep(10)
            num += 1

        }
    }
    Thread.sleep(5000)
    println(num)
}

/**
 * not ok
 */
inline fun <reified T> isStringType(): Boolean {
    println(T::class.qualifiedName)
    return T::class.isInstance(CharSequence::class)
//    return CharSequence::class.isInstance(T::class)


}

inline fun <reified T> compute(): Result<T> {
    val result: T = T::class.constructors.filter { c -> c.parameters.isEmpty() }.first().call()
    return Success(result)
}


sealed class Result<T>
class Success<T>(val result: T) : Result<T>()
class Failure(val reason: Throwable) : Result<Nothing>()

