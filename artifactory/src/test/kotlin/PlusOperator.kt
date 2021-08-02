package be.benoit.artifactory

class ArrayND {
    var nDimensionalArray: ArrayList<Double> = arrayListOf()
    private var shape = arrayOf<Int>()

    constructor (ndArray: Array<Double>, shape: Array<Int>) {
        nDimensionalArray = arrayListOf(*ndArray)
        this.shape = shape
    }

    private fun whatIsTheShape(): Array<Int> {
        return shape
    }

    fun add(b: ArrayND): ArrayND {
        if (shape.contentEquals(b.whatIsTheShape())) {
            val x = arrayListOf<Double>()
            for (i in 0 until nDimensionalArray.size) {
                x.add(nDimensionalArray[i] + b.nDimensionalArray[i])
            }
            return ArrayND(x.toTypedArray(), shape)
        } else {
            return ArrayND(arrayOf(), arrayOf())
        }
    }

    operator fun plus(other: ArrayND): ArrayND {
        return add(other)
    }

    fun print() {
        TODO("Work in more than one dimension")
        print("[")
        for (i in nDimensionalArray) {
            print("$i ")
        }
        print("]")
    }

    override fun toString(): String {
        return nDimensionalArray.joinToString(" ", "[", "]")
    }
}

fun main(args: Array<String>) {
    println(ArrayND(arrayOf(1.0), arrayOf(1, 2, 3)) + ArrayND(arrayOf(1.0), arrayOf(1, 2, 3)))
}