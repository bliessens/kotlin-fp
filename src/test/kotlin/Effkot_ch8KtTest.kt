import effective.kotlin.chapter8.Database
import org.junit.jupiter.api.Disabled
import kotlin.test.Test

class Effkot_ch8KtTest {

    @Disabled
    @Test
    fun getDatabase() {
        println(Database("jdbc:as400://salpi200", "LIMSERVER", "SERVERLIM").dataBaseName())
    }

    @Test
    fun testRepeat() {
        "benoit".repeat(3, ::println)
    }

    fun <T> T.repeat(count: Int, action: (T) -> Unit): Unit {
        for (i in 0 until count) {
            action(this)
        }
    }

    @Test
    fun name() {
        for (i in 0 totEnMet 5) {
            print("ok ")
        }
        println()
        for (i in -1 tot 4) {
            print("ok ")
        }
    }


    infix fun Int.totEnMet(max: Int): IntRange {
        require(max > 0)
        return IntRange(this, max)
    }

    infix fun Int.tot(max: Int): IntRange {
        require(max > 1)
        return IntRange(this, max - 1)
    }

}