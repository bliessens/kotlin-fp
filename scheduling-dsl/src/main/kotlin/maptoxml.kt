package maptoxml

@DslMarker
annotation class XmlDsl

fun xml(rootTag: String, init: XmlDocumentBuilder.() -> Unit): String {
    return XmlDocumentBuilder(rootTag).apply(init).toString()
}

sealed interface Node {
    val indent: Int
}

sealed interface ParentNode : Node {
    val tags: MutableList<Tag>

    fun node(name: String, init: NodeBuilder.() -> Unit) {
        tags += NodeBuilder(indent + INDENT, name).apply(init).build()
    }
}

const val INDENT = 2

@XmlDsl
class XmlDocumentBuilder(val root: String) : ParentNode {

    override val tags = mutableListOf<Tag>()
    override val indent: Int
        get() = 0

    override fun toString(): String {
        return StringBuilder().apply {
            append("<$root>\n")
            tags.forEach {
                append(it.toString())
            }
            append("</$root>")
        }.toString()
    }
}

//@XmlDsl
class NodeBuilder internal constructor(override val indent: Int, val name: String) : ParentNode {

    override val tags = mutableListOf<Tag>()
    private val attributes = mutableListOf<Pair<String, String>>()

    val attribute = Attribute()

//    operator fun String.unaryPlus(): AttributeValue {
//        return AttributeValue(this, this@TagBuilder)
//    }

//    infix fun String.attribute(attribValue: String) {
//        attributes += this to attribValue
//    }

    inner class AttributeNameHolder internal constructor(val name: String, val builder: NodeBuilder) {
        infix fun withValue(value: String) {
            builder.attributes += Pair(this.name, value)
        }
    }

    inner class Attribute internal constructor() {
        infix fun named(name: String): AttributeNameHolder {
            return AttributeNameHolder(name, this@NodeBuilder)
        }
    }

    internal fun build(): Tag {
        return Tag(indent, name, attributes.toMap(), tags)
    }
}

//@XmlDsl
class Tag(
    private val indent: Int,
    val name: String,
    private val attributes: Map<String, String>,
    val tags: MutableList<Tag>,
) {

    override fun toString(): String {
        val indent = " ".repeat(indent)
        return StringBuilder().apply {
            append(indent).append("<$name")
            attributes.forEach {
                append(" ${it.key}='${it.value}'")
            }
            if (tags.isEmpty())
                append("/>\n")
            else {
                append(">\n")
                tags.forEach {
                    append(it.toString())
                }
                append(indent).append("</$name>\n")
            }
        }.toString()
    }
}
