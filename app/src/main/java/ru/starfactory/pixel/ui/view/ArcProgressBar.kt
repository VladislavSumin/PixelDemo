package ru.starfactory.pixel.ui.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.starfactory.pixel.ui.theme.PixelDemoTheme

@Composable
fun ArcProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    strokeWidth: Dp = 6.dp,
    frameWidth: Dp = 12.dp,
    content: @Composable BoxScope .() -> Unit = {}
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
    ) {
        val frameColor =MaterialTheme.colors.onPrimary
        val arcColor =MaterialTheme.colors.primary
        Canvas(modifier = Modifier.fillMaxSize()) {
            val offset = Offset(frameWidth.toPx() / 2, frameWidth.toPx() / 2)
            val size = Size(this.size.width - 2 * offset.x, this.size.height - 2 * offset.y)
            // frame
            drawArc(
                color = frameColor,
                startAngle = 180f - 30f,
                sweepAngle = 180f + 60f,
                useCenter = false,
                style = Stroke(frameWidth.toPx(), cap = StrokeCap.Round),
                topLeft = offset,
                size = size
            )

            // progress
            drawArc(
                color = arcColor,
                startAngle = 180f - 30f,
                sweepAngle = (180f + 60f) * progress,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round),
                topLeft = offset,
                size = size
            )
        }

        // Internal content
        Box(
            Modifier
                .align(Alignment.Center)
                .padding(frameWidth * 1.2f)
                .aspectRatio(4f / 3f),
            content = content
        )
    }
}

@OptIn(ExperimentalTextApi::class)
@Preview(showBackground = true)
@Composable
fun ArcProgressBarPreview() {
    PixelDemoTheme {
        ArcProgressBar(
            modifier = Modifier
                .size(300.dp)
                .background(Color.Yellow),
            progress = 0.63f
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = .5f))
            )
        }
    }
}
