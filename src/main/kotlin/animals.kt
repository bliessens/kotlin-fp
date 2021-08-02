package people

import scopes.apply

@DslMarker
annotation class AnimalDSL

@AnimalDSL
fun animals(init: AnimalsBuilder.() -> Unit): Animals {
    return AnimalsBuilder()
        .apply { init() }
        .run { build() }
}

class AnimalsBuilder() {
    var animals: Collection<Animal> = listOf()

    fun animal(init: AnimalBuilder.() -> Unit): Unit {
        animals = animals.plus(AnimalBuilder().apply(init).let { builder -> builder.build() })
//        return animals.last()
    }

    fun build(): Animals {
        return Animals(animals)
    }
}

class Animals(val animals: Collection<Animal>) {

}

@AnimalDSL
fun animal(init: AnimalBuilder.() -> Unit): Animal {
//    return AnimalBuilder().apply(init).let { b -> b.build() }
    val builder = AnimalBuilder()
    builder.init()
    return builder.build()
}

class AnimalBuilder() {
    var name: String = ""
        set(aName) {
            field = aName
        }

    fun build(): Animal {
        return Animal(name)
    }

}

data class Animal(val type: String)

