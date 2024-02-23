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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
                    text = stringResource(R.string.dashboard),
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
    greetingMessage: String = stringResource(R.string.hello),
    name: String = stringResource(R.string.vijay)
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
                    text = stringResource(R.string.greeting_message_format, greetingMessage, name),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = stringResource(R.string.date_format, day, month, date, year),
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Image(
                painter = painterResource(id = R.drawable.hri),
                contentDescription = stringResource(R.string.profile_picture),
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
            .clickable {
                onClick()
            }
            .then(modifier)
            .padding(10.dp)

    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(spacing),
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(text = stringResource(R.string.job_stats), style = titleTextStyle)

            Divider(color = Color.LightGray)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(R.string.jobs, uiState.totalJobs), style = contentTextStyle)
                Text(
                    text = stringResource(
                        R.string.of_jobs_completed,
                        uiState.completedJobs,
                        uiState.totalJobs
                    ),
                    style = contentTextStyle
                )
            }

            JobStatusBar(
                totalJobs = uiState.totalJobs,
                completedJobs = uiState.completedJobs,
                yetToStart = uiState.yetToStart,
                inProgress = uiState.inProgress,
                cancelled = uiState.cancelled,
                inCompleted = uiState.inCompleted,
                completedJobsColor = DarkMintGreen,
                yetToStartColor = LightPurple,
                inProgressColor = LightBlue,
                cancelledColor = Yellow,
                inCompletedColor = LightRed
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatusText(
                    progressText = stringResource(R.string.yet_to_start),
                    progress = uiState.yetToStart.toString(),
                    progressColor = LightPurple,
                    textStyle = contentTextStyle
                )
                StatusText(
                    progressText = stringResource(R.string.progress_in),
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
                    progressText = stringResource(R.string.cancelled),
                    progress = uiState.cancelled.toString(),
                    progressColor = Yellow,
                    textStyle = contentTextStyle
                )
                StatusText(
                    progressText = stringResource(R.string.completed),
                    progress = uiState.completedJobs.toString(),
                    progressColor = DarkMintGreen,
                    textStyle = contentTextStyle
                )
            }

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                StatusText(
                    progressText = stringResource(R.string.in_completed),
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
        Text(text = stringResource(R.string.progress_format, progressText, progress), style = textStyle)
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

            Text(text = stringResource(R.string.invoice_stats), modifier = Modifier.fillMaxWidth(), style = titleTextStyle)

            Divider(color = Color.LightGray)

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.total_value_format, uiState.totalValue), style = contentTextStyle)

                Text(text = stringResource(R.string.collected_format, uiState.paid), style = contentTextStyle)
            }

            InvoiceStatusBar(
                totalValue = uiState.totalValue,
                draft = uiState.draft,
                pending = uiState.pending,
                paid = uiState.paid,
                badDebit = uiState.badDebit,
                draftColor = Yellow,
                pendingColor = LightBlue,
                paidColor = DarkMintGreen,
                badDebitColor = LightRed
            )

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                StatusText(
                    progressText = stringResource(R.string.draft),
                    progress = uiState.draft.toString().prefixDollar(),
                    progressColor = Yellow,
                    textStyle = contentTextStyle
                )

                StatusText(
                    progressText = stringResource(R.string.pending),
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
                    progressText = stringResource(R.string.paid),
                    progress = uiState.paid.toString().prefixDollar(),
                    progressColor = DarkMintGreen,
                    textStyle = contentTextStyle
                )

                StatusText(
                    progressText = stringResource(R.string.bad_debit),
                    progress = uiState.badDebit.toString().prefixDollar(),
                    progressColor = LightRed,
                    textStyle = contentTextStyle
                )
            }
        }
    }
}