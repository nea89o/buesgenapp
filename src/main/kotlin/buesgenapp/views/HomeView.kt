package buesgenapp.views

import buesgenapp.controller.UserController
import javafx.geometry.Orientation
import javafx.scene.Parent
import tornadofx.View
import tornadofx.splitpane

class HomeView : View() {
    val userController by inject<UserController>()
    override val root: Parent = splitpane(Orientation.HORIZONTAL) {
        val balanceView = BalanceView()
        add(balanceView)
        val transactionView = TransactionView()
        add(transactionView)
        transactionView.balance.bindBidirectional(balanceView.selectedBalance)
    }
}
