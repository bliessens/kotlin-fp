package chapter3

// with lambdas
fun compose(a: (Int) -> Int, b: (Int) -> Int): (Int) -> Int = { x -> a(b(x)) }

val doubleOfPlusOne = compose({ y -> y * 2 }, { x -> x + 1 })
val squareOfTriple = compose(a = { x -> x * x }, b = { x -> x * 3 })

fun plusOne(x: Int) = x + 1
fun square(x: Int): Int {
    return x * x
}

val squareOfPlusOne = compose(::square, ::plusOne)

// polymorphic version of compose()
fun <T> polyCompose(a: (T) -> T, b: (T) -> T): (T) -> T = { i -> a(b(i)) }

fun <T, U, V> polyCompose2(a: (U) -> V, b: (T) -> U): (T) -> V = { i -> a(b(i)) }

val cubeOfTriple = polyCompose2({ x: Float -> x * x * x }, { x: Float -> x * 3 })

fun addOne(a: Int): (Int) -> Int = { a + 1 }
val add: (Int) -> ((Int) -> Int) = { a -> { b -> a + b } }


// exercise 3.5


val double: (Int) -> Double = { x -> (x * 2).toDouble() }
val triple: (Int) -> Int = { x -> x * 3 }
fun <T, U, V> higherOrderCompose(): ((U) -> V) -> ((T) -> U) -> ((T) -> V) = { f -> { g -> { x -> f(g(x)) } } }

data class Person(val givenName: String, val name: String)

fun main(args: Array<String>) {
    println(doubleOfPlusOne(2))
    println(squareOfPlusOne(3))
    println(squareOfTriple(2))

    println(cubeOfTriple(3.4f))

    println(addOne(3)(5))
    println(add(3)(5))

    println(higherOrderCompose<Int, Int, Double>()(double)(triple)(5))

}


