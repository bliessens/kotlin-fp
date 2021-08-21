package maptoxml

@DslMarker
annotation class XmlDsl

val languagesAndAuthors = mapOf("Java" to "Gosling", "Lisp" to "McCarthy", "Ruby" to "Matz")


fun xml(rootTag: String, init: XmlDocumentBuilder.() -> Unit): String {
    return XmlDocumentBuilder(rootTag).apply(init).toString()
}

interface Node {
    val tags: MutableList<Tag>
    val indent: Int

    fun tag(name: String, init: TagBuilder.() -> Unit) {
        tags += TagBuilder(indent + INDENT, name).apply(init).build()
    }
}

const val INDENT = 2

@XmlDsl
class XmlDocumentBuilder(val root: String) : Node {

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
class TagBuilder(override val indent: Int, val name: String) : Node {

    override val tags = mutableListOf<Tag>()
    private val attributes = mutableListOf<Pair<String, String>>()

    val attribute = Attribute()

//    operator fun String.unaryPlus(): AttributeValue {
//        return AttributeValue(this, this@TagBuilder)
//    }

    infix fun String.attribute(attribValue: String) {
        attributes += this to attribValue
    }

    inner class AttributeNameHolder(val name: String, val builder: TagBuilder) {
        infix fun withValue(value: String) {
            builder.attributes += Pair(this.name, value)
        }
    }

    inner class Attribute() {
        infix fun named(name: String): AttributeNameHolder {
            return AttributeNameHolder(name, this@TagBuilder)
        }
    }

    fun build(): Tag {
        return Tag(indent, name, attributes.toMap(), tags)
    }
}

//@XmlDsl
class Tag(val indent: Int, val name: String, val attributes: Map<String, String>, val tags: MutableList<Tag>) {

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

fun main() {

    val xml = xml("languages") {
        languagesAndAuthors.entries.forEach { entry ->
            val (title, author) = entry
            tag("language") {
                //attribute("title") withValue title
                tag("author") {
                    "name" attribute author
                }
                tag("related") {
                    "type" attribute "books"
                    tag("book") {
                        "title" attribute "Java 9 Modules"
                    }
                }
            }
        }
        tag("language") {
            "title" attribute "benoit"

        }
    }
    println(xml)
}
