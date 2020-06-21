package buesgenapp.models

import buesgenapp.transactionListBinding
import buesgenapp.userBinding
import buesgenapp.uuidMapping
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.util.*
import java.util.concurrent.Callable
import javax.json.JsonObject
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class Balance : JsonModel {
    val formattedName
        get() = "${owner?.username}:${label}"
    val idProperty = SimpleStringProperty()
    var id by idProperty.uuidMapping()

    val labelProperty = SimpleStringProperty()
    var label by labelProperty

    val ownerProperty = SimpleStringProperty()
    var ownerId by ownerProperty.uuidMapping()
    var owner by userBinding(ownerProperty)

    var allTransactions by transactionListBinding(idProperty)

    val totalProperty by lazy {
        Bindings.createIntegerBinding(
            Callable {
                if (id == UUID.fromString("00000000-0000-0000-0000-000000000001")) {
                    return@Callable Int.MAX_VALUE
                }
                allTransactions.map { it.changeAsSeenBy(this.id) }.sum()
            },
            allTransactions
        )
    }
    val total by object : ReadOnlyProperty<Any, Int> {
        override fun getValue(thisRef: Any, property: KProperty<*>) =
            totalProperty.get()
    }

    override fun toJSON(json: JsonBuilder): Unit = with(json) {
        add("id", idProperty.get())
        add("label", label)
        add("owner", ownerProperty.get())
    }

    override fun updateModel(json: JsonObject): Unit = with(json) {
        idProperty.set(string("id"))
        label = string("label")
        ownerProperty.set(string("owner"))
    }

    override fun toString(): String = formattedName
}