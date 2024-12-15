package org.breera.project.book.presentation.book_detail.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import org.breera.project.core.presentation.DarkBlue
import org.breera.project.core.presentation.PulseAnimation
import org.breera.project.core.presentation.SandYellow

/**
 * Created by Breera Hanif on 10/12/2024.
 */
@Composable
fun BlurredImage(
    url: String?,
    isFavourite: Boolean,
    onFavouriteClick: () -> Unit,
    onBackClick: () -> Unit,
    content: @Composable () -> Unit
) {
    var imageLoadResult by remember {
        mutableStateOf<Result<Painter>?>(null)
    }
    val painter = rememberAsyncImagePainter(
        model = url,
        onSuccess = {
            val size = it.painter.intrinsicSize
            imageLoadResult = if (size.width > 1 && size.height > 1) {
                Result.success(it.painter)
            } else {
                Result.failure(Exception("Invalid image dimensions"))
            }
        },
        onError = {
            it.result.throwable.printStackTrace()
            // imageLoadResult = Result.failure(it.result.throwable)
        }
    )

    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(Modifier.fillMaxWidth().weight(0.3f)) {
                Image(
                    painter = painter,
                    contentScale = ContentScale.Crop,
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize()
                        .background(DarkBlue)
                        .blur(20.dp)
                )

                IconButton(
                    modifier = Modifier.padding(10.dp)
                        .statusBarsPadding()
                        .size(40.dp),
                    onClick = {
                        onBackClick()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "rating",
                        modifier = Modifier
                            .statusBarsPadding()
                            .size(AssistChipDefaults.IconSize),
                        tint = Color.White
                    )
                }

            }
            Column(
                modifier = Modifier.weight(0.7f).fillMaxSize()
                    .background(Color.White)
            ) {
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(Modifier.fillMaxHeight(0.15f))
            ElevatedCard(
                modifier = Modifier
                    .width(200.dp)
                    .aspectRatio(3 / 4f),
                shape = RoundedCornerShape(8.dp)
            ) {
                AnimatedContent(targetState = imageLoadResult) {
                    when (it) {
                        null -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                PulseAnimation(
                                    modifier = Modifier.size(60.dp)
                                        .align(Alignment.Center)
                                )
                            }
                        }

                        else -> {
                            Box() {
                                Image(
                                    painter = painter,
                                    contentScale = if (it.isSuccess) {
                                        ContentScale.Crop
                                    } else {
                                        ContentScale.Fit
                                    },
                                    contentDescription = "",
                                    modifier = Modifier
                                        .fillMaxSize()
                                )
                                IconButton(
                                    onClick = onFavouriteClick,
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .background(
                                            brush = Brush.radialGradient(
                                                colors = listOf(
                                                    SandYellow, Color.Transparent
                                                ),
                                                radius = 70f
                                            )
                                        )
                                ) {
                                    Icon(
                                        imageVector = if (isFavourite) {
                                            Icons.Filled.Favorite
                                        } else {
                                            Icons.Outlined.FavoriteBorder
                                        },
                                        tint = Color.Red,
                                        contentDescription = "mmm"
                                    )
                                }
                            }
                        }
                    }
                }
            }
            content()
        }
    }
}

