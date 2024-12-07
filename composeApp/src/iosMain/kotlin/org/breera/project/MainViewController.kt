package org.breera.project

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import io.ktor.client.engine.darwin.Darwin

fun MainViewController() = ComposeUIViewController { App(remember { Darwin.create() }) }