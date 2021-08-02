package be.benoit.effkot

/**
 * interface properties and how to implement them
 */

interface User {
    val username: String
}

class PrivateUser(override val username: String) : User

class SubscribingUser(val email: String) : User {
    override val username: String
        get() = email.substringBefore('@')
}

class FaceBookUser(val id: Int) : User {
    override val username = getFacebookUserName(id)
}

fun getFacebookUserName(id: Int) = ""
