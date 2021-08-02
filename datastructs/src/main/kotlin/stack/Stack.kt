package stack

interface Stack<T : Any> {

    fun pop(): T?

    fun push(element: T): T

    fun peek(): T?

    val size: Int

    val isEmpty: Boolean
        get() = size == 0
}

fun <T : Any> stackOf(vararg elements: T): Stack<T> = ArrayListStack<T>().apply { elements.forEach { push(it) } }

fun <T : Any> List<T>.toStack(): Stack<T> = stackOf<T>().apply { this@toStack.forEach { push(it) } }

fun <K : Any, V : Any> Map<K, V>.toStack(): Stack<Pair<K, V>> = this.toList().toStack()

private class ArrayListStack<T : Any> : Stack<T> {

    private val elements = arrayListOf<T>()

    override fun pop(): T? {
        return if (elements.isEmpty())
            null
        else
            elements.removeAt(elements.size - 1)
    }

    override fun push(element: T): T = element.also { elements.add(element) }

    override fun peek(): T? {
        return elements.lastOrNull()
    }

    override val size: Int
        get() = elements.size
}
