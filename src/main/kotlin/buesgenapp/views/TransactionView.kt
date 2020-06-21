package buesgenapp.views

import buesgenapp.controller.TransactionController
import buesgenapp.models.Balance
import buesgenapp.models.Transaction
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.input.Clipboard
import javafx.scene.input.DataFormat
import javafx.scene.text.Font
import tornadofx.*
import java.util.concurrent.Callable

class TransactionView : View() {
    val transactionController by inject<TransactionController>()
    val balance = SimpleObjectProperty<Balance>()
    val list = balance.select { SimpleListProperty(it.allTransactions) }
    override val root = vbox {
        text {
            font = Font.font(15.0)
            textProperty().bind(balance
                .select { it.idProperty }
                .select { SimpleStringProperty("Click to copy balance id: $it\n" +
                        "People need this to send you money.") })
            onLeftClick {
                Clipboard.getSystemClipboard().setContent(mapOf(DataFormat.PLAIN_TEXT to balance.value.id.toString()))
            }
        }
        tableview(list) {
            column("Label", Transaction::label)
            readonlyColumn("From", Transaction::from).cellFormat {
                text = it?.formattedName
            }
            readonlyColumn("To", Transaction::to).cellFormat {
                text = it?.formattedName
            }
            column<Transaction, Number>("Change") {
                Bindings.createIntegerBinding(Callable {
                    it.value.changeAsSeenBy(balance.get().id)
                }, balance)
            }
        }
    }

}