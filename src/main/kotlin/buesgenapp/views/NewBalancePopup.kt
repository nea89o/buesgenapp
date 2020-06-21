package buesgenapp.views

import buesgenapp.controller.BalanceController
import buesgenapp.controller.UserController
import javafx.beans.property.SimpleStringProperty
import javafx.scene.Parent
import tornadofx.*

class NewBalancePopup : View("New Balance") {
    val balanceController by inject<BalanceController>()
    val userController by inject<UserController>()
    val labelProp = SimpleStringProperty()
    override val root: Parent = vbox {
        text("Create New Balance")
        form {
            fieldset {
                field("Label") {
                    textfield(labelProp).requestFocus()
                }
                button("Create Balance") {
                    isDefaultButton = true
                    setOnAction {
                        runAsync {
                            balanceController.createBalance(userController.currentManagedUser.value.id, labelProp.valueSafe)
                        } ui {
                            close()
                        }
                    }
                }
            }
        }
    }

}