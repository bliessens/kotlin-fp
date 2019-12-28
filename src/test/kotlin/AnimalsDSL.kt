import org.junit.jupiter.api.Test
import people.animal
import people.animals
import kotlin.test.assertEquals

internal class AnimalsDSL {

    @Test
    internal fun testSingleAnimal() {
        val animal = animal {
            name = "giraffe"
        }

        assertEquals("giraffe", animal.type)
    }

    @Test
    internal fun testAnimals() {
        val animals = animals {
            animal {
                name = "giraffe"
            }
            animal {
                name = "elephant"
            }
        }

        assertEquals(2, animals.animals.size)
        assertEquals("giraffe", animals.animals.first().type)
        assertEquals("elephant", animals.animals.last().type)
    }

}