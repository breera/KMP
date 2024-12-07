package org.breera.project.book.presentation.book_list.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import composemultiplaformsample.composeapp.generated.resources.Res
import composemultiplaformsample.composeapp.generated.resources.search_hint
import org.breera.project.core.presentation.DarkBlue
import org.breera.project.core.presentation.DesertWhite
import org.breera.project.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource

@Composable
fun BookSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onItemSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = SandYellow,
            backgroundColor = SandYellow
        )
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = modifier.background(
                shape = RoundedCornerShape(100),
                color = DesertWhite
            ).minimumInteractiveComponentSize(),
            shape = RoundedCornerShape(100),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onItemSearch()
                }
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = DarkBlue,
                focusedBorderColor = SandYellow
            ),
            placeholder = {
                Text(text = stringResource(Res.string.search_hint))
            },
            singleLine = true,
            leadingIcon = {
                IconButton(onClick = onItemSearch) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            trailingIcon = {
                AnimatedVisibility(query.isNotBlank()) {

                    IconButton(onClick = { onQueryChange.invoke("") }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            },
        )
    }
}