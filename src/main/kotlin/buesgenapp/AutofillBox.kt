package buesgenapp

import javafx.beans.property.Property
import javafx.collections.ObservableList
import javafx.event.EventTarget
import np.com.ngopal.control.AutoFillTextBox
import tornadofx.attachTo

inline fun <reified T> EventTarget.autoFillBox(
    property: Property<T>,
    data: ObservableList<T>,
    filterMode: Boolean = false,
    op: AutoFillTextBox<T>.() -> Unit = {}
) = AutoFillTextBox(data).attachTo(this, op) {
    property.bind(it.listview.selectionModel.selectedItemProperty())
    it.filterMode = filterMode
    it.attachTo(this)
}