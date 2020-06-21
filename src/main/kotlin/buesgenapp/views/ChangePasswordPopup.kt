package buesgenapp.views

import buesgenapp.controller.UserController
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleStringProperty
import javafx.scene.Parent
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import tornadofx.*

class ChangePasswordPopup : View() {
    val userController by inject<UserController>()
    val newPasswordProp = SimpleStringProperty()
    val confirmPasswordProp = SimpleStringProperty()

    val user = userController.currentManagedUser
    override val root: Parent = vbox {
        form {
            fieldset {
                text(Bindings.concat("Change password for ", Bindings.selectString(user, "username")))
                field("New Password") {
                    passwordfield(newPasswordProp)
                }
                field("Confirm Password") {
                    passwordfield(confirmPasswordProp)
                }
                button("Change Password") {
                    enableWhen { Bindings.equal(newPasswordProp, confirmPasswordProp) }
                    setOnAction {
                        runAsync {
                            userController.changePassword(user.get(), newPasswordProp.valueSafe)
                        } ui {
                            if (it) {
                                close()
                            } else {
                                alert(
                                    Alert.AlertType.ERROR, "Could not change password",
                                    "Invalid or blank password.", ButtonType.OK
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}