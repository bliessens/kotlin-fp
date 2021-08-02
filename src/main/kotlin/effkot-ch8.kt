package effective.kotlin.chapter8

import com.mchange.v2.c3p0.ComboPooledDataSource
import java.sql.DriverManager

val name: String = User("benoit").name


fun getDatabaseName(url: String, user: String, pwd: String): String {
    DriverManager.getConnection(url, user, pwd).use { conn -> return conn.metaData.databaseProductName }
}

class Database(val aUrl: String, val aUser: String, val aPassword: String) {
    val dataSource: ComboPooledDataSource by lazy { initDataSource() }


    fun initDataSource(): ComboPooledDataSource {
        val pool = ComboPooledDataSource()
        pool.jdbcUrl = aUrl
        pool.user = aUser
        pool.password = aPassword
        pool.minPoolSize = 3
        return pool
    }

    fun dataBaseName(): String {
//        check(dataSource.numIdleConnections >= 1) {
//            "DataSource is not initialized"
//        }
        return dataSource.connection.use { conn -> return conn.metaData.databaseProductName }
    }
}


fun <T> T.bapply(block: T.() -> Unit): T {
    this.block()
    return this
}

fun <T> T.balso(block: T.() -> Unit): T {
    this.block()
    return this
}

fun <T, R> T.brun(block: T.() -> R): R {
    return this.block()
}

fun <T, R> T.blet(block: (T) -> R): R {
    return block(this)
}

fun main(args: Array<String>) {
    val a = StringBuilder("benoit")
        .bapply { append(" liessens") }
//        .run { toString() }
//        .run { capitalize() }
//        .let { println(this) }
    println(a)
    val b = StringBuilder("benoit").apply { append(" liessens") }
    println(b)

    val c = StringBuilder("liessens").brun { toString() }
    println(c)
    val d = StringBuilder("liessens").run { toString() }
    println(d)
}