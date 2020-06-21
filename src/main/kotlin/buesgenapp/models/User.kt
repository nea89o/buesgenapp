package buesgenapp.models;

import buesgenapp.balanceListBinding
import buesgenapp.controller.BalanceController
import buesgenapp.uuidMapping
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.time.LocalDate
import java.time.Period
import javax.json.JsonObject

class User() : JsonModel {
    val idProperty = SimpleStringProperty()
    var id by idProperty.uuidMapping()

    val usernameProperty = SimpleStringProperty()
    var username by usernameProperty

    val passwordProperty = SimpleStringProperty()
    var password by passwordProperty

    val birthdayProperty = SimpleObjectProperty<LocalDate>()
    var birthday by birthdayProperty

    val allBalances by balanceListBinding(idProperty)

    val age: Int get() = Period.between(birthday, LocalDate.now()).years
    override fun toJSON(json: JsonBuilder): Unit = with(json) {
        add("username", username)
        add("id", idProperty.get())
        add("birthday", birthday)
        add("password", password)
    }

    override fun updateModel(json: JsonObject):Unit = with(json) {
        username = string("username")
        idProperty.set(string("id"))
        birthday = date("birthday")
        password = string("password")
    }
}