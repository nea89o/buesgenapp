package buesgenapp.views

import buesgenapp.controller.UserController
import javafx.geometry.Orientation
import tornadofx.View
import tornadofx.splitpane

class AdminView : View() {
    val userController by inject<UserController>()
    override val root = splitpane(Orientation.HORIZONTAL) {
        val userView = UserView()
        userController.currentManagedUser.bind(userView.selectedUser)
        add(userView)
        val balanceView = BalanceView()
        add(balanceView)
        val transactionView = TransactionView()
        add(transactionView)
        transactionView.balance.bindBidirectional(balanceView.selectedBalance)

    }

}
