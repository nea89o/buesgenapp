package buesgenapp.views

import buesgenapp.controller.SaveLoadController
import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*

class StartView : View() {
    val saveLoadController by inject<SaveLoadController>()
    override val root = vbox {
        text("Buesgen Banking v1.0") {
            alignment = Pos.TOP_CENTER
            font = Font.font(100.0)
        }
        button("Login") {
            alignment = Pos.TOP_CENTER
            font = Font.font(40.0)
            saveLoadController.generateDefaultData()
            action {
                replaceWith<LoginView>()
            }
        }
    }

}
