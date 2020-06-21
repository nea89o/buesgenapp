package buesgenapp.views

import buesgenapp.controller.UserController
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.time.LocalDate

class NewUserPopup : View("New User") {
    val usernameProp = SimpleStringProperty()
    val passwordProp = SimpleStringProperty()
    val birthdayProp = SimpleObjectProperty<LocalDate>()
    val userController by inject<UserController>()
    override val root = vbox {
        form {
            fieldset {
                field("Username") {
                    textfield(usernameProp)
                }
                field("Password") {
                    passwordfield(passwordProp)
                }
                field("Birthday") {
                    datepicker(birthdayProp)
                }
                button("Create User") {
                    setOnAction {
                        runAsync {
                            if (birthdayProp.value == null){
                                return@runAsync false
                            }
                            return@runAsync userController.createUser(
                                usernameProp.valueSafe,
                                passwordProp.valueSafe,
                                birthdayProp.value
                            )
                        } ui {
                            close()
                        }
                    }

                }
            }
        }
    }
}
