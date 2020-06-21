package buesgenapp.controller

import buesgenapp.models.Balance
import buesgenapp.models.LoadedData
import buesgenapp.models.User
import javafx.collections.ListChangeListener
import tornadofx.Controller
import tornadofx.save
import tornadofx.toJSON
import tornadofx.toModel
import java.io.File
import java.io.FileNotFoundException
import java.time.LocalDate
import java.util.*

class SaveLoadController : Controller() {

    val file = File("content.json")
    var data = try {
        file.inputStream().use {
            it.toJSON().toModel<LoadedData>()
        }
    } catch (e: FileNotFoundException) {
        LoadedData()
    }.also {
        val invalidator = ListChangeListener<Any> {
            save()
        }
        it.balances.addListener(invalidator)
        it.users.addListener(invalidator)
        it.transactions.addListener(invalidator)
    }

    fun generateDefaultData() {
        if (data.users.isEmpty()) {
            val root = User().also { u ->
                u.username = "root"
                u.password = "root"
                u.birthday = LocalDate.of(0, 1, 1)
                u.id = UUID.fromString("00000000-0000-0000-0000-000000000000")
                data.users.add(u)
            }
            val income = Balance().also{b->
                b.ownerId = root.id
                b.id = UUID.fromString("00000000-0000-0000-0000-000000000001")
                b.label = "income"
                data.balances.add(b)
            }
        }

    }

    fun save() {
        data.also { d ->
            file.outputStream().use {
                d.save(it)
            }
        }
    }

}