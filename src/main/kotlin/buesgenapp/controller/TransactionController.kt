package buesgenapp.controller

import buesgenapp.models.Transaction
import tornadofx.Controller
import java.util.*

class TransactionController : Controller() {
    val saveLoadController by inject<SaveLoadController>()
    val balanceController by inject<BalanceController>()
    val transactions = saveLoadController.data.transactions
    fun transactionList(toOrFromId: UUID) = transactions.filtered {
        it.fromId == toOrFromId || it.toId == toOrFromId
    }

    fun makeTransaction(fromId: UUID, toId: UUID, label: String, change: Int): Boolean {
        if (balanceController.findBalance(toId) == null) return false
        if (balanceController.findBalance(fromId)?.total ?: 0 < change || change < 0) return false
        Transaction().also {
            it.fromId = fromId
            it.toId = toId
            it.label = label
            it.change = change
            it.id = UUID.randomUUID()
            transactions.add(it)
        }
        return true
    }
}
