package zuper.dev.android.dashboard.ui.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import zuper.dev.android.dashboard.R

@Composable
fun StatusText(
    progressText: String,
    progress: String,
    progressColor: Color,
    textStyle: TextStyle
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Box(
            modifier = Modifier
                .width(8.dp)
                .height(8.dp)
                .background(color = progressColor)
        )
        Text(
            text = stringResource(R.string.progress_format, progressText, progress),
            style = textStyle
        )
    }
}

@Composable
fun StatsBar(
    list: List<StatsBarInfo>,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(15.dp)
    ) {
        var prevWidth = 0F
        val sumOfList = list.sumOf { it.count }
        list.forEach {
            drawRoundRect(
                color = it.color,
                size = Size(size.width - prevWidth, size.height),
                cornerRadius = CornerRadius(10F)
            )
            prevWidth += (it.count * size.width) / sumOfList
        }
    }
}

@Composable
fun StatsHeader(
    modifier: Modifier = Modifier,
    startTextStyle: TextStyle,
    endTextStyle: TextStyle,
    startText: String,
    endText: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = startText, style = startTextStyle
        )
        Text(
            text = endText, style = endTextStyle
        )
    }
}

data class StatsBarInfo(
    val color: Color,
    val count: Int
)