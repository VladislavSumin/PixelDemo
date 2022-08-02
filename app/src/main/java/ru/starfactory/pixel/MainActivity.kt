package ru.starfactory.pixel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.starfactory.pixel.ui.theme.PixelDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PixelDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@OptIn(ExperimentalTextApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PixelDemoTheme {
        CircularProgressBar(
            Modifier
                .background(Color.Red.copy(alpha = .3f))
                .padding(16.dp)
                .size(200.dp),
            progress = .70f
        ) {
            AutoSizeText(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = (-10).dp)
                    .padding(32.dp),
                text = "78",
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 400.sp,
                    platformStyle = PlatformTextStyle(false),
                ),
            )
            Text(
                text = "km/h",
                Modifier
                    .align(Alignment.Center)
                    .offset(y = 40.dp)
            )
        }
    }
}

@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    strokeWidth: Dp = 6.dp,
    frameWidth: Dp = 10.dp,
    content: @Composable BoxScope .() -> Unit = {}
) {
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val offset = Offset(frameWidth.toPx() / 2, frameWidth.toPx() / 2)
            val size = Size(this.size.width - 2 * offset.x, this.size.height - 2 * offset.y)
            // frame
            drawArc(
                color = Color.Blue,
                startAngle = 180f - 30f,
                sweepAngle = 180f + 60f,
                useCenter = false,
                style = Stroke(frameWidth.toPx(), cap = StrokeCap.Round),
                topLeft = offset,
                size = size
            )

            // progress
            drawArc(
                color = Color.Red,
                startAngle = 180f - 30f,
                sweepAngle = (180f + 60f) * progress,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round),
                topLeft = offset,
                size = size
            )
        }

        content()
    }
}

@Composable
fun AutoSizeText(
    text: String,
    textStyle: TextStyle = LocalTextStyle.current,
    modifier: Modifier = Modifier,
) {
    var scaledTextStyle by remember { mutableStateOf(textStyle) }
    var readyToDraw by remember { mutableStateOf(false) }

    Text(
        text,
        modifier.drawWithContent {
            if (readyToDraw) {
                drawContent()
            }
        },
        style = scaledTextStyle,
        softWrap = false,
        onTextLayout = { textLayoutResult ->
            if (textLayoutResult.didOverflowWidth) {
                scaledTextStyle =
                    scaledTextStyle.copy(fontSize = scaledTextStyle.fontSize * 0.9)
            } else {
                readyToDraw = true
            }
        }
    )
}