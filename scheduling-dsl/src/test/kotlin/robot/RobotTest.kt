package robot

import org.junit.jupiter.api.Test

class RobotTest {

    @Test
    fun testRobot() {
        Robot operate {
            it turns left
            it turns right
            it runs fast
        }
    }

    @Test
    fun testUnintendedRobot() {
        Robot operate {
            it turns left
            it turns right
            it runs fast

            it operate {
                it turns left
                it turns right
                it runs fast
            }
        }
    }

    @Test
    fun testImprovedRobot() {
        Robot2 operate {
            it turns left
            it turns right
            it runs fast

//            it operate {
            it turns left
            it turns right
            it runs fast
//            }
        }
    }
}
