package buesgenapp.models

import buesgenapp.balanceBinding
import buesgenapp.controller.BalanceController
import buesgenapp.uuidMapping
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.util.*
import javax.json.JsonObject

class Transaction : JsonModel {
    val idProperty = SimpleStringProperty()
    var id by idProperty.uuidMapping()

    val fromProperty = SimpleStringProperty()
    var fromId by fromProperty.uuidMapping()
    val from: Balance? by balanceBinding(fromProperty)

    val toProperty = SimpleStringProperty()
    var toId by toProperty.uuidMapping()
    val to: Balance? by balanceBinding(toProperty)

    val labelProperty = SimpleStringProperty()
    var label by labelProperty

    // As seen by to
    val changeProperty = SimpleIntegerProperty()
    var change by changeProperty
    fun changeAsSeenBy(balanceId: UUID) = if (fromId == balanceId) {
        -change
    } else {
        change
    }

    override fun toJSON(json: JsonBuilder): Unit = with(json) {
        add("id", idProperty.get())
        add("from", fromProperty.get())
        add("to", toProperty.get())
        add("label", label)
        add("change", change)
    }

    override fun updateModel(json: JsonObject): Unit = with(json) {
        idProperty.set(string("id"))
        fromProperty.set(string("from"))
        toProperty.set(string("to"))
        label = string("label")
        change = int("change")!!
    }

}