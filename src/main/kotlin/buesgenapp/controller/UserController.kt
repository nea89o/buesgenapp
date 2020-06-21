package buesgenapp.controller

import buesgenapp.controller.UserController.LoginResult.*
import buesgenapp.models.User
import javafx.beans.property.SimpleObjectProperty
import tornadofx.Controller
import tornadofx.singleAssign
import java.time.LocalDate
import java.util.*

class UserController : Controller() {
    val saveLoadController by inject<SaveLoadController>()
    val users = saveLoadController.data.users
    var currentManagedUser = SimpleObjectProperty<User>()
    var currentUser by singleAssign<User>()

    enum class LoginResult {
        NO_USER, INVALID_PASSWORD, SUCCESS, ADMIN
    }

    fun createUser(username: String, password: String, birthday: LocalDate): Boolean {
        User().apply {
            this.birthday = birthday
            this.password = password
            this.username = username
            this.id = UUID.randomUUID()
            users.add(this)
        }
        return true
    }

    fun changePassword(user: User, newPassword: String): Boolean {
        val newPassword = newPassword.trim()
        return if (newPassword.isEmpty()) {
            false
        } else {
            user.password = newPassword
            saveLoadController.save()
            true
        }
    }

    fun login(username: String, password: String): LoginResult {
        val user = users.find { it.username == username } ?: return NO_USER
        return if (user.password == password) {
            currentManagedUser.set(user)
            currentUser = user
            if (user.username == "root") ADMIN
            else SUCCESS
        } else {
            INVALID_PASSWORD
        }
    }

    fun findUser(userId: UUID) = users.find { it.id == userId }

}