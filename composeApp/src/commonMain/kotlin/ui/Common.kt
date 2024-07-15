package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.svg.SvgDecoder

@Composable
fun ShimmerImage(
    modifier: Modifier = Modifier,
    source: String
) {
    SubcomposeAsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalPlatformContext.current)
            .data(source)
            .crossfade(true)
            .decoderFactory(SvgDecoder.Factory())
            .build(),
        loading = {
            ShimmerPlaceholder(modifier = Modifier.size(48.dp))
        },
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}