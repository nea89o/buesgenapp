package buesgenapp

import buesgenapp.controller.BalanceController
import buesgenapp.controller.TransactionController
import buesgenapp.controller.UserController
import buesgenapp.models.Balance
import buesgenapp.models.Transaction
import buesgenapp.models.User
import javafx.beans.property.StringPropertyBase
import javafx.collections.ObservableList
import tornadofx.find
import java.util.*
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// binders.kt: but i wanna be a girl
// @past me: this is a shitty joke.

fun StringPropertyBase.uuidMapping() = object : ReadWriteProperty<Any, UUID> {
    override fun getValue(thisRef: Any, property: KProperty<*>): UUID = UUID.fromString(this@uuidMapping.value)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: UUID) {
        this@uuidMapping.value = value.toString()
    }
}

fun balanceBinding(balanceIdProp: StringPropertyBase) =
    object : ReadWriteProperty<Any, Balance?> {
        val controller by lazy { find<BalanceController>() }

        override fun getValue(thisRef: Any, property: KProperty<*>) =
            controller.findBalance(UUID.fromString(balanceIdProp.get()))

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Balance?) {
            balanceIdProp.set(value?.id?.toString())
        }
    }

fun balanceListBinding(ownerIdProperty: StringPropertyBase) =
    object : ReadOnlyProperty<Any, ObservableList<Balance>> {
        val controller by lazy { find<BalanceController>() }
        override fun getValue(thisRef: Any, property: KProperty<*>): ObservableList<Balance> =
            controller.balanceList(UUID.fromString(ownerIdProperty.get()))
    }

fun userBinding(userIdProp: StringPropertyBase) =
    object : ReadWriteProperty<Any, User?> {
        val controller by lazy { find<UserController>() }
        override fun getValue(thisRef: Any, property: KProperty<*>): User? =
            controller.findUser(UUID.fromString(userIdProp.get()))

        override fun setValue(thisRef: Any, property: KProperty<*>, value: User?) {
            userIdProp.set(value?.id?.toString())
        }
    }

fun transactionListBinding(balanceIdProp: StringPropertyBase) =
    object : ReadWriteProperty<Any, ObservableList<Transaction>> {
        val controller by lazy { find<TransactionController>() }
        override fun getValue(thisRef: Any, property: KProperty<*>): ObservableList<Transaction> =
            controller.transactionList(UUID.fromString(balanceIdProp.get()))

        override fun setValue(thisRef: Any, property: KProperty<*>, value: ObservableList<Transaction>) {
            TODO("WHAT?")
        }
    }