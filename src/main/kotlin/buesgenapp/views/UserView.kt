package buesgenapp.views

import buesgenapp.controller.UserController
import buesgenapp.models.User
import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Insets
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import tornadofx.*

class UserView : View() {
    val userController by inject<UserController>()
    val selectedUser = SimpleObjectProperty<User>()
    override val root = vbox {
        button("New User"){
            setOnAction {
                NewUserPopup().openModal()
            }
        }
        tableview(userController.users) {
            column("Id", User::idProperty)
            column("Username", User::usernameProperty)
            column("Birthday", User::birthdayProperty)
            readonlyColumn("Age", User::age) {
                cellFormat {
                    text = it.toString()
                    if (it < 18) {
                        tableRow?.background = Background(BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY))
                    }
                }
            }
            selectedUser.bind(selectionModel.selectedItemProperty())
        }
    }
}
