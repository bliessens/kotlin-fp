package robot

object Robot {
    infix fun turns(dir: Direction) = println("it turns $dir")
    infix fun runs(speed: Speed) = println("it runs $speed")

    infix fun operate(init: DirectionSpeedReceiver.(Robot) -> Unit): Unit {
        DirectionSpeedReceiver.init(this)
    }
}

object DirectionSpeedReceiver {
    val left = Direction.Left
    val right = Direction.Right
    val fast = Speed.Fast
}

enum class Direction { Left, Right }
enum class Speed { Fast, Slow }


/**
 * Slightly improved design to make DSL even more strict:
 * Ensure the operate function can n=only be called upon
 * the Robot2 class, thus prohibiting nested calls
 */
class Robot2 {

    infix fun turns(dir: Direction) = println("Robot2 turns $dir")
    infix fun runs(speed: Speed) = println("Robot2 runs $speed")

    companion object {
        infix fun operate(init: DirectionSpeedReceiver.(Robot2) -> Unit) {
            Robot2().run { DirectionSpeedReceiver.init(this) }
        }
    }
}
