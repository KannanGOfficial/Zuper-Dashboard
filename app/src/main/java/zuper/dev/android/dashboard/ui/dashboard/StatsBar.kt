package zuper.dev.android.dashboard.ui.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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

data class StatsBarInfo(
    val color: Color,
    val count: Int
)

@Preview
@Composable
fun PreviewStatsBar() {

    val list = listOf(
        StatsBarInfo(
            color = Color.Red,
            50
        ),

        StatsBarInfo(
            color = Color.Green,
            60
        ),

        StatsBarInfo(
            color = Color.Yellow,
            20
        ),

        StatsBarInfo(
            color = Color.Magenta,
            20
        ),

        StatsBarInfo(
            color = Color.Blue,
            20
        )
    )
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            StatsBar(
                list = list.sortedBy { it.count }
            )
        }
    }
}

