package org.breera.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import composemultiplaformsample.composeapp.generated.resources.Res
import composemultiplaformsample.composeapp.generated.resources.compose_multiplatform
import composemultiplaformsample.composeapp.generated.resources.eg
import composemultiplaformsample.composeapp.generated.resources.fr
import composemultiplaformsample.composeapp.generated.resources.id
import composemultiplaformsample.composeapp.generated.resources.jp
import composemultiplaformsample.composeapp.generated.resources.mx
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.breera.project.book.presentation.book_list.BookListRoot
import org.breera.project.book.presentation.book_list.BookListViewModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val viewModel = koinViewModel<BookListViewModel>()
    BookListRoot(
        viewModel = viewModel,
        onBookClick = {

        }
    )
}

@Composable
@Preview
fun App2() {
    MaterialTheme {
        Column(modifier = Modifier.padding(20.dp)) {
            var showCountries by remember { mutableStateOf(false) }
            var location by remember { mutableStateOf("Europe/Paris") }
            var timeAtLocation by remember { mutableStateOf("No location selected") }
            Text(
                timeAtLocation,
                style = TextStyle(fontSize = 20.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
            )

            DropdownMenu(
                expanded = showCountries,
                onDismissRequest = { showCountries = false },
                content = {
                    countries().forEach { (name, zone, image) ->
                        DropdownMenuItem(onClick = {
                            timeAtLocation = currentTimeAt(name, zone) ?: "Invalid"
                            showCountries = false
                        },
                            text = { Text(name) },
                            leadingIcon = {
                                Image(
                                    modifier = Modifier.size(50.dp).padding(end = 10.dp),
                                    painter = painterResource(image), contentDescription = null
                                )
                            }
                        )
                    }
                }
            )
//            TextField(
//                value = location,
//                modifier = Modifier.padding(top = 10.dp),
//                onValueChange = { location = it })
            Button(
                modifier = Modifier.padding(top = 10.dp),
                onClick = { (!showCountries).also { showCountries = it } }) {
                Text("Select Location")
            }
        }
    }
}

@Composable
@Preview
fun App1() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "todays date is ${todayData()}",
                modifier = Modifier.padding(20.dp),
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }
}

fun LocalDateTime.format() = toString().substringBefore('T')

fun todayData(): String {

    val now = Clock.System.now()
    val zone = TimeZone.currentSystemDefault()
    return now.toLocalDateTime(zone).format()
}

fun currentTimeAt(location: String, zone: TimeZone): String {
    fun LocalTime.formatted() = "$hour:$minute:$second"

    val time = Clock.System.now()
    val localTime = time.toLocalDateTime(zone).time

    return "The time in $location is ${localTime.formatted()}"
}

fun countries() = listOf(
    Country("Japan", TimeZone.of("Asia/Tokyo"), Res.drawable.jp),
    Country("France", TimeZone.of("Europe/Paris"), Res.drawable.fr),
    Country("Mexico", TimeZone.of("America/Mexico_City"), Res.drawable.mx),
    Country("Indonesia", TimeZone.of("Asia/Jakarta"), Res.drawable.id),
    Country("Egypt", TimeZone.of("Africa/Cairo"), Res.drawable.eg)

)

data class Country(val name: String, val timeZone: TimeZone, val image: DrawableResource)
