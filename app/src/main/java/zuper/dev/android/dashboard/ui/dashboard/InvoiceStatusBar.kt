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
import zuper.dev.android.dashboard.ui.theme.Yellow

@Composable
fun InvoiceStatusBar(
    totalValue: Long,
    draft: Long,
    pending: Long,
    paid: Long,
    badDebit: Long,
    draftColor: Color,
    pendingColor: Color,
    paidColor: Color,
    badDebitColor: Color,
) {

    val draftWidthRatio = remember {
        Animatable(0F)
    }

    val pendingWidthRatio = remember {
        Animatable(0F)
    }

    val paidWidthRatio = remember {
        Animatable(0F)
    }

    val badDebitWidthRatio = remember {
        Animatable(0F)
    }


    LaunchedEffect(key1 = draft) {
        draftWidthRatio.animateTo(
            targetValue = (draft.toFloat() / totalValue)
        )
    }

    LaunchedEffect(key1 = pending) {
        pendingWidthRatio.animateTo(
            targetValue = (pending.toFloat() / totalValue)
        )
    }

    LaunchedEffect(key1 = paid) {
        paidWidthRatio.animateTo(
            targetValue = (paid.toFloat() / totalValue)
        )
    }

    LaunchedEffect(key1 = badDebit) {
        badDebitWidthRatio.animateTo(
            targetValue = (badDebit.toFloat() / totalValue)
        )
    }

    val background = Color.White

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(15.dp)
    ) {

        val canvasWidth = size.width

        drawRoundRect(
            color = background,
            size = size,
            cornerRadius = CornerRadius(10F)
        )


        val completedJobsWidth = draftWidthRatio.value * canvasWidth
        val pendingWidth = pendingWidthRatio.value * canvasWidth + completedJobsWidth
        val paidWidth = paidWidthRatio.value * canvasWidth + pendingWidth
        val badDebitWidth = badDebitWidthRatio.value * canvasWidth + paidWidth


        drawRoundRect(
            color = badDebitColor,
            size = Size(badDebitWidth, size.height),
            cornerRadius = CornerRadius(10F)
        )

        drawRoundRect(
            color = paidColor,
            size = Size(paidWidth, size.height),
            cornerRadius = CornerRadius(10F)
        )

        drawRoundRect(
            color = pendingColor,
            size = Size(pendingWidth, size.height),
            cornerRadius = CornerRadius(10F)
        )

        drawRoundRect(
            color = draftColor,
            size = Size(completedJobsWidth, size.height),
            cornerRadius = CornerRadius(10F)
        )

    }

}


@Preview(showBackground = true)
@Composable
fun PreviewInvoiceStatusBarAnimatable() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp), contentAlignment = Alignment.Center
    ) {
        InvoiceStatusBar(
            totalValue = 60,
            draft = 25,
            pending = 15,
            paid = 10,
            badDebit = 5,
            draftColor = DarkMintGreen,
            pendingColor = LightBlue,
            paidColor = Yellow,
            badDebitColor = LightPurple,
        )
    }

}