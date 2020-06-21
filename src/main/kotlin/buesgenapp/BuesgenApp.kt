package buesgenapp

import buesgenapp.views.StartView
import javafx.stage.Stage
import tornadofx.App
import tornadofx.launch

class BuesgenApp : App(StartView::class) {
    override fun start(stage: Stage) =
        super.start(stage.also { it.isMaximized = true })
}

fun main(args: Array<String>) = launch<BuesgenApp>(args)