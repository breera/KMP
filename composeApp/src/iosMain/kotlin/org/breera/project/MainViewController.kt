package org.breera.project

import androidx.compose.ui.window.ComposeUIViewController
import org.breera.project.app.App
import org.breera.project.di.initKoin

fun MainViewController() = ComposeUIViewController(
    // called only once
    configure = { initKoin() }
) {
    App()
}