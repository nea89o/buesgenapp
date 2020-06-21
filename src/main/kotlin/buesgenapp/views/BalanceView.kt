package buesgenapp.views

import buesgenapp.controller.UserController
import buesgenapp.models.Balance
import buesgenapp.models.User
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*
import java.util.concurrent.Callable

class BalanceView() : View() {
    val selectedBalance = SimpleObjectProperty<Balance>()
val userController by inject<UserController>()
    override val root = vbox {
        buttonbar {
            button("Change Password"){
                setOnAction{
                    ChangePasswordPopup().openModal()
                }
            }
            button("New Balance") {
                setOnAction {
                    NewBalancePopup().openModal()
                }
            }
            button("New transaction") {
                setOnAction {
                    NewTransactionPopup().openModal()
                }
            }
        }
        tableview(userController.currentManagedUser.select { Bindings.createObjectBinding(Callable {
            it.allBalances
        }, it.idProperty) }) {
            column("Label", Balance::label)
            column("Total", Balance::totalProperty)
            onUserSelect(1, action = selectedBalance::set)
        }
    }

}
