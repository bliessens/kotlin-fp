package scopes

inline fun <T> T.alsoB(block: T.() -> Unit): T {
    this.block()
    return this;
}

inline fun <T> T.apply(block: T.() -> Unit): T {
    block()
    return this
}
