package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.IntegerRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpaceApp(Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun ArtSpaceApp(modifier: Modifier = Modifier) {
    var imageOrder by remember { mutableStateOf(1) }
    val imagerPainter = determinateImage(imageOrder)

    Column(
        modifier = modifier
            .padding(top = 50.dp, bottom = 4.dp, start = 10.dp, end = 10.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .shadow(8.dp)
                .background(Color.White)
                .padding(16.dp)
                .width(400.dp)
                .height(460.dp),
            contentAlignment = Alignment.Center
        )
        {
            Image(
                painter = painterResource(id = imagerPainter),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.width(400.dp)

            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        ImageDescription(imageOrder,
            Modifier
                .padding(16.dp))
        ActionButtons(
            { if (imageOrder == 1) imageOrder = 3 else imageOrder-- },
            { if (imageOrder == 3) imageOrder = 1 else imageOrder++ },
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        )
    }

}

@Composable
fun ActionButtons(onClickPrev: () -> Unit, onClickNext: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = onClickPrev,
            modifier = Modifier.width(120.dp)
        )
        {
            Text(text = stringResource(R.string.previous_button))
        }
        Button(onClick = onClickNext,
            modifier = Modifier.width(120.dp)
        )
        {
            Text(text = stringResource(R.string.next_button))
        }
    }
}

@Composable
fun ImageDescription(imageOrder: Int, modifier: Modifier = Modifier) {
    val title = determinateTitle(imageOrder)
    val (author, year) = determinateInfo(imageOrder)
    val description = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(stringResource(id = author))
        }
        append(stringResource(id = year))
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .background(color = Color.LightGray)
            .padding(16.dp)
            .width(400.dp)
    ) {
        Text(text = stringResource(id = title), fontSize = 28.sp)
        Text(text = description)
    }
}

private fun determinateInfo(imageOrder: Int): Pair<Int, Int> {
    var author = 0
    var year = 0
    when (imageOrder) {
        1 -> {
            author = R.string.author_1
            year = R.string.year_1
        }

        2 -> {
            author = R.string.author_2
            year = R.string.year_2
        }

        else -> {
            author = R.string.author_3
            year = R.string.year_3
        }
    }
    return Pair(author, year)
}

private fun determinateTitle(imageOrder: Int): Int {
    return when (imageOrder) {
        1 -> R.string.image_1_title
        2 -> R.string.image_2_title
        else -> R.string.image_3_title
    }
}

private fun determinateImage(imageOrder: Int, modifier: Modifier = Modifier): Int {
    return when (imageOrder) {
        1 -> R.drawable.image_1
        2 -> R.drawable.image_2
        else -> R.drawable.image_3
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpaceAppPreview() {
    ArtSpaceTheme {
        ArtSpaceApp(Modifier.fillMaxSize())
    }
}