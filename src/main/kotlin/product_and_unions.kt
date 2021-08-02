package combotypes

sealed class Either<L, R> {
    class Left<L, R>(val left: L) : Either<L, R>()
    class Right<L, R>(val right: R) : Either<L, R>()
}

sealed class Trither<L, M, R> {
    class Left<L, M, R>(val left: L) : Trither<L, M, R>()
    class Middle<L, M, R>(val middle: M) : Trither<L, M, R>()
    class Right<L, M, R>(val right: R) : Trither<L, M, R>()
}


fun <L, R> test(either: Either<L, R>) {

    when (either) {
        is Either.Left -> println(either.left)
        is Either.Right -> println(either.right)
    }

}

typealias VehicleType = String
typealias FuelType = String

fun vatRateOf(v: VehicleType, f: FuelType): Int {
    return 21
}
