package buesgenapp.models

import javafx.beans.property.SimpleListProperty
import tornadofx.*
import javax.json.JsonObject

class LoadedData : JsonModel {
    val usersProperty = SimpleListProperty<User>(observableListOf())
    var users by usersProperty

    val balancesProperty = SimpleListProperty<Balance>(observableListOf())
    var balances by balancesProperty

    val transactionsProperty = SimpleListProperty<Transaction>(observableListOf())
    var transactions by transactionsProperty
    override fun toJSON(json: JsonBuilder): Unit = with(json) {
        add("users", users)
        add("balances", balances)
        add("transactions", transactions)
    }

    override fun updateModel(json: JsonObject): Unit = with(json) {
        users = json.jsonArray("users")?.map { it.asJsonObject().toModel<User>() }?.asObservable()
            ?: observableListOf()
        balances = json.jsonArray("balances")?.map { it.asJsonObject().toModel<Balance>() }?.asObservable()
            ?: observableListOf()
        transactions = json.jsonArray("transactions")?.map { it.asJsonObject().toModel<Transaction>() }?.asObservable()
            ?: observableListOf()
    }
}