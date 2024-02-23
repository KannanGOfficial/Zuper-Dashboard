package zuper.dev.android.dashboard.ui.dashboard

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import zuper.dev.android.dashboard.ui.theme.DarkMintGreen
import zuper.dev.android.dashboard.ui.theme.LightBlue
import zuper.dev.android.dashboard.ui.theme.LightPurple
import zuper.dev.android.dashboard.ui.theme.LightRed
import zuper.dev.android.dashboard.ui.theme.Yellow

@Composable
fun JobStatusBar(
    totalJobs: Int,
    completedJobs: Int,
    yetToStart: Int,
    inProgress: Int,
    cancelled: Int,
    inCompleted: Int,
    completedJobsColor: Color,
    yetToStartColor: Color,
    inProgressColor: Color,
    cancelledColor: Color,
    inCompletedColor: Color,
    modifier: Modifier = Modifier
) {

    val completedJobWidthRatio = remember {
        Animatable(0F)
    }

    val yetToStartWidthRatio = remember {
        Animatable(0F)
    }

    val inProgressWidthRatio = remember {
        Animatable(0F)
    }

    val cancelledWidthRatio = remember {
        Animatable(0F)
    }

    val inCompletedWidthRatio = remember {
        Animatable(0F)
    }

    LaunchedEffect(key1 = completedJobs) {
        completedJobWidthRatio.animateTo(
            targetValue = (completedJobs.toFloat() / totalJobs)
        )
    }

    LaunchedEffect(key1 = yetToStart) {
        yetToStartWidthRatio.animateTo(
            targetValue = (yetToStart.toFloat() / totalJobs)
        )
    }

    LaunchedEffect(key1 = inProgress) {
        inProgressWidthRatio.animateTo(
            targetValue = (inProgress.toFloat() / totalJobs)
        )
    }

    LaunchedEffect(key1 = cancelled) {
        cancelledWidthRatio.animateTo(
            targetValue = (cancelled.toFloat() / totalJobs)
        )
    }

    LaunchedEffect(key1 = inCompleted) {
        inCompletedWidthRatio.animateTo(
            targetValue = (inCompleted.toFloat() / totalJobs)
        )
    }
    val background = Color.White

    Canvas(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth()
            .height(15.dp)

    ) {

        val canvasWidth = size.width

        drawRoundRect(
            color =background,
            size = size,
            cornerRadius = CornerRadius(10F)
        )


        val completedJobsWidth = completedJobWidthRatio.value * canvasWidth
        val yetToStartWidth = yetToStartWidthRatio.value * canvasWidth + completedJobsWidth
        val inProgressWidth = inProgressWidthRatio.value * canvasWidth + yetToStartWidth
        val cancelledWidth = cancelledWidthRatio.value * canvasWidth + inProgressWidth
        val inCompletedWidth = inCompletedWidthRatio.value * canvasWidth + cancelledWidth

        drawRoundRect(
            color = inCompletedColor,
            size = Size(inCompletedWidth, size.height),
            cornerRadius = CornerRadius(10F)
        )

        drawRoundRect(
            color = cancelledColor,
            size = Size(cancelledWidth, size.height),
            cornerRadius = CornerRadius(10F)
        )

        drawRoundRect(
            color = inProgressColor,
            size = Size(inProgressWidth, size.height),
            cornerRadius = CornerRadius(10F)
        )

        drawRoundRect(
            color = yetToStartColor,
            size = Size(yetToStartWidth, size.height),
            cornerRadius = CornerRadius(10F)
        )

        drawRoundRect(
            color = completedJobsColor,
            size = Size(completedJobsWidth, size.height),
            cornerRadius = CornerRadius(10F)
        )

    }

}


@Preview(showBackground = true)
@Composable
fun PreviewBarChartAnimatable() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp), contentAlignment = Alignment.Center
    ) {
        JobStatusBar(
            totalJobs = 60,
            completedJobs = 25,
            yetToStart = 15,
            inProgress = 10,
            cancelled = 5,
            inCompleted = 5,
            completedJobsColor = DarkMintGreen,
            yetToStartColor = LightBlue,
            inProgressColor = Yellow,
            cancelledColor = LightPurple,
            inCompletedColor = LightRed
        )
    }

}