package buesgenapp.controller

import buesgenapp.models.Balance
import javafx.collections.ObservableList
import tornadofx.Controller
import java.util.*

class BalanceController : Controller() {
    private val saveLoadController by inject<SaveLoadController>()
    val balances = saveLoadController.data.balances
    fun findBalance(balanceId: UUID) = balances.find { it.id == balanceId }

    fun createBalance(ownerId: UUID, label: String): Balance {
        val balance = Balance()
        balance.label = label
        balance.ownerId = ownerId
        balance.id = UUID.randomUUID()
        balances.add(balance)
        return balance
    }

    fun balanceList(ownerId: UUID): ObservableList<Balance> = balances.filtered { it.ownerId == ownerId }

}
