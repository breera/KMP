package org.breera.project.book.presentation.book_list.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import composemultiplaformsample.composeapp.generated.resources.Res
import composemultiplaformsample.composeapp.generated.resources.book_error_2
import composemultiplaformsample.composeapp.generated.resources.eg
import org.breera.project.book.domain.Book
import org.breera.project.core.presentation.DarkBlue
import org.breera.project.core.presentation.LightBlue
import org.breera.project.core.presentation.SandYellow
import org.jetbrains.compose.resources.painterResource
import kotlin.math.round

@Composable
fun BookItem(
    book: Book,
    modifier: Modifier,
    onClick: () -> Unit = {}
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.clickable {
                onClick.invoke()
            },
        color = LightBlue.copy(0.2f)
    ) {
        //ConstraintLayout() {
        //   val (image, title, author, rating) = createRefs()
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier.height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                var imageLoadResult by remember {
                    mutableStateOf<Result<Painter>?>(null)
                }
                val painter = rememberAsyncImagePainter(
                    model = book.imageUrl,
                    onSuccess = {
                        imageLoadResult =
                            if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                                Result.success(it.painter)
                            } else {
                                Result.failure(Exception("Invalid image size"))
                            }
                    },
                    onError = {
                        it.result.throwable.printStackTrace()
                        imageLoadResult = Result.failure(it.result.throwable)
                    }
                )
                when (val result = imageLoadResult) {
                    null -> {
                        CircularProgressIndicator()
                    }

                    else -> {
                        Image(
                            painter = if (result.isSuccess) painter else painterResource(Res.drawable.book_error_2),
                            contentDescription = book.title,
                            contentScale = if (result.isSuccess) ContentScale.Crop else ContentScale.Fit,
                            modifier = Modifier.aspectRatio(
                                ratio = 0.65f,
                                matchHeightConstraintsFirst = true
                            )
                        )
                    }
                }
            }
            Column(
                modifier = Modifier.padding(start = 16.dp)
                    .fillMaxHeight().weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                book.authors.firstOrNull()?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    book.averageRating?.let {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${round(it * 10) / 10.0}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = SandYellow
                            )
                        }
                    }
                }

            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = DarkBlue
            )
        }
    }
}