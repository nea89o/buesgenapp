package buesgenapp.views

import buesgenapp.controller.TransactionController
import buesgenapp.controller.UserController
import buesgenapp.models.Balance
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import tornadofx.*
import java.util.*

class NewTransactionPopup : View("New Transaction") {
    data class LabeledBalance(val balance: Balance) {
        override fun toString(): String = balance.label
    }

    val transactionController by inject<TransactionController>()
    val userController by inject<UserController>()
    val labelProp = SimpleStringProperty()
    val fromProp = SimpleObjectProperty<LabeledBalance>()
    val toProp = SimpleStringProperty()
    val amountProp = SimpleIntegerProperty()
    override val root = vbox {
        form {
            fieldset {
                field("Label") {
                    textfield(labelProp)
                }
                field("From") {
                    val labeledBalances = userController.currentManagedUser.get().allBalances.map { LabeledBalance(it) }
                    combobox(fromProp, labeledBalances)
                    text {
                        textProperty().bind(fromProp.select {
                            SimpleStringProperty(it.balance.total.toString())
                        })
                    }
                }
                field("To") {
                    textfield(toProp) {
                        tooltip("In the format of 00000000-0000-0000-0000-000000000000. " +
                                "Can be found in the balance view of that user")
                    }
                }
                field("Amount") {
                    textfield(amountProp) {
                        filterInput {
                            it.controlNewText.isInt() && it.controlNewText.toInt() >= 0 &&
                                    it.controlNewText.toInt() <= fromProp.value?.balance?.total ?: Int.MAX_VALUE
                        }
                    }
                }
                button("Make Transaction") {
                    setOnAction {
                        runAsync {
                            val amount = amountProp.value.toInt()
                            if (amount < 0 || amount > fromProp.get().balance.total) {
                                return@runAsync false
                            }
                            try {
                                return@runAsync transactionController.makeTransaction(
                                    fromProp.get().balance.id,
                                    UUID.fromString(toProp.valueSafe),
                                    labelProp.value,
                                    amount
                                )
                            } catch (e: Exception) {
                                return@runAsync false
                            }
                        } ui {
                            if (it) {
                                close()
                            } else {
                                alert(
                                    Alert.AlertType.ERROR, "Invalid Transaction", """Possible causes
                                    |Insufficient funds
                                    |Invalid target
                                    |Invalid source
                                """.trimMargin(), ButtonType.OK
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}