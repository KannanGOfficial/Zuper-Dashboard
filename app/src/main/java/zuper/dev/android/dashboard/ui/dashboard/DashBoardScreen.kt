package zuper.dev.android.dashboard.ui.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import zuper.dev.android.dashboard.R
import zuper.dev.android.dashboard.data.DataRepository
import zuper.dev.android.dashboard.data.remote.ApiDataSource
import zuper.dev.android.dashboard.ui.theme.Yellow
import zuper.dev.android.dashboard.ui.theme.DarkMintGreen
import zuper.dev.android.dashboard.ui.theme.LightRed
import zuper.dev.android.dashboard.ui.theme.LightBlue
import zuper.dev.android.dashboard.ui.theme.LightPurple
import zuper.dev.android.dashboard.utils.extension.formatTitleCase
import zuper.dev.android.dashboard.utils.extension.prefixDollar
import zuper.dev.android.dashboard.utils.navigation.Screens
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardScreen(
    navHostController: NavHostController
) {
    val apiDataSource = ApiDataSource()
    val dataRepository = DataRepository(apiDataSource)
    val viewModel = viewModel<DashboardViewModel> {
        DashboardViewModel(dataRepository)
    }

    val currentDate = LocalDate.now()
    val day = currentDate.dayOfWeek.name
    val month = currentDate.month.name
    val date = currentDate.dayOfMonth
    val year = currentDate.year

    val cardBorderModifier = Modifier.border(
        BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(5.dp)
    )

    val appBarBorderModifier = Modifier.border(
        BorderStroke(1.dp, Color.LightGray)
    )


    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {

            TopAppBar(title = {
                Text(
                    text = "Dashboard",
                    style = MaterialTheme.typography.titleLarge
                )
            }, modifier = appBarBorderModifier)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {

                Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {

                    GreetingCard(
                        day = day.formatTitleCase(),
                        date = date,
                        month = month.formatTitleCase(),
                        year = year,
                        modifier = cardBorderModifier
                    )

                    JobStatusCard(
                        uiState = viewModel.uiState,
                        modifier = cardBorderModifier,
                        titleTextStyle = MaterialTheme.typography.titleMedium,
                        contentTextStyle = MaterialTheme.typography.bodySmall
                    ) {
                        navHostController.navigate(Screens.JOBS_SCREEN.route)
                    }

                    InvoiceStatusCard(
                        uiState = viewModel.uiState,
                        modifier = cardBorderModifier,
                        titleTextStyle = MaterialTheme.typography.titleMedium,
                        contentTextStyle = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
fun GreetingCard(
    day: String,
    date: Int,
    month: String,
    year: Int,
    modifier: Modifier,
    greetingMessage: String = "Hello",
    name: String = "Vijay"
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .padding(15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = "$greetingMessage, $name !\uD83D\uDC4B",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "$day, $month ${date}th $year",
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Image(
                painter = painterResource(id = R.drawable.hri),
                contentDescription = "profile_picture",
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun JobStatusCard(
    uiState: DashBoardUiState,
    modifier: Modifier,
    titleTextStyle: TextStyle,
    contentTextStyle: TextStyle,
    onClick: () -> Unit
) {
    val spacing = 10.dp
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .padding(10.dp)
            .clickable {
                onClick()
            }
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(spacing),
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(text = "Job Stats", style = titleTextStyle)

            Divider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${uiState.totalJobs} Jobs", style = contentTextStyle)
                Text(
                    text = "${uiState.completedJobs} of ${uiState.totalJobs} Jobs completed",
                    style = contentTextStyle
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatusText(
                    progressText = "Yet to Start",
                    progress = uiState.yetToStart.toString(),
                    progressColor = LightPurple,
                    textStyle = contentTextStyle
                )
                StatusText(
                    progressText = "In-Progress",
                    progress = uiState.inProgress.toString(),
                    progressColor = LightBlue,
                    textStyle = contentTextStyle
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatusText(
                    progressText = "cancelled",
                    progress = uiState.cancelled.toString(),
                    progressColor = Yellow,
                    textStyle = contentTextStyle
                )
                StatusText(
                    progressText = "Completed",
                    progress = uiState.completedJobs.toString(),
                    progressColor = DarkMintGreen,
                    textStyle = contentTextStyle
                )
            }

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                StatusText(
                    progressText = "In-Completed",
                    progress = uiState.inCompleted.toString(),
                    progressColor = LightRed,
                    textStyle = contentTextStyle
                )
            }
        }

    }
}

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
        Text(text = "$progressText ($progress)", style = textStyle)
    }

}

@Composable
fun InvoiceStatusCard(
    uiState: DashBoardUiState = DashBoardUiState(),
    modifier: Modifier,
    titleTextStyle: TextStyle,
    contentTextStyle: TextStyle
) {
    val spacing = 10.dp
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .padding(10.dp),
    ) {

        Column(verticalArrangement = Arrangement.spacedBy(spacing)) {

            Text(text = "Invoice Stats", modifier = Modifier.fillMaxWidth(), style = titleTextStyle)

            Divider()

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Total Value ($${uiState.totalValue})", style = contentTextStyle)

                Text(text = "Collected ($${uiState.paid})", style = contentTextStyle)
            }

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                StatusText(
                    progressText = "Draft",
                    progress = uiState.draft.toString().prefixDollar(),
                    progressColor = Yellow,
                    textStyle = contentTextStyle
                )

                StatusText(
                    progressText = "Pending",
                    progress = uiState.pending.toString().prefixDollar(),
                    progressColor = LightBlue,
                    textStyle = contentTextStyle
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                StatusText(
                    progressText = "Paid",
                    progress = uiState.paid.toString().prefixDollar(),
                    progressColor = DarkMintGreen,
                    textStyle = contentTextStyle
                )

                StatusText(
                    progressText = "Bad Debit",
                    progress = uiState.badDebit.toString().prefixDollar(),
                    progressColor = LightRed,
                    textStyle = contentTextStyle
                )
            }
        }
    }
}