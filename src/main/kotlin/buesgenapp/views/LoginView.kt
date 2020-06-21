package buesgenapp.views

import buesgenapp.controller.UserController
import buesgenapp.controller.UserController.LoginResult
import buesgenapp.models.User
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import tornadofx.*
import java.time.LocalDate


class LoginView : View() {
    val userController: UserController by inject()
    val username = SimpleStringProperty()
    val password = SimpleStringProperty()

    override val root = form {
        runAsync {
            if (userController.users.isEmpty()) {
                userController.createUser("roman", "roman", LocalDate.now())
            }
        }
        fieldset {
            field("Username") {
                textfield(username)
            }
            field("Password") {
                passwordfield(password)
            }
            buttonbar {
                button("Log In") {
                    isDefaultButton = true
                    action {
                        runAsync {
                            userController.login(username.valueSafe, password.valueSafe)
                        } ui {
                            password.value = ""
                            username.value = ""
                            when (it) {
                                LoginResult.NO_USER -> alert(
                                    Alert.AlertType.ERROR, "Login Error",
                                    "This user couldn't be found in our database.", ButtonType.OK
                                )
                                LoginResult.INVALID_PASSWORD -> alert(
                                    Alert.AlertType.ERROR, "Login Error",
                                    "Invalid password.", ButtonType.OK
                                )
                                LoginResult.ADMIN -> replaceWith<AdminView>()
                                LoginResult.SUCCESS -> replaceWith<HomeView>()
                            }
                        }
                    }
                }
            }
        }
    }

}
