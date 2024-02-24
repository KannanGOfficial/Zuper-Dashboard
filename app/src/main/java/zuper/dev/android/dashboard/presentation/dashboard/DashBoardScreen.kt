package zuper.dev.android.dashboard.presentation.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import zuper.dev.android.dashboard.R
import zuper.dev.android.dashboard.presentation.theme.Yellow
import zuper.dev.android.dashboard.presentation.theme.DarkMintGreen
import zuper.dev.android.dashboard.presentation.theme.LightRed
import zuper.dev.android.dashboard.presentation.theme.LightBlue
import zuper.dev.android.dashboard.presentation.theme.LightPurple
import zuper.dev.android.dashboard.utils.extension.prefixDollar
import zuper.dev.android.dashboard.utils.timeformatter.TimeFormatter
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardScreen(
    gotoJobScreen: () -> Unit
) {

    val viewModel = hiltViewModel<DashboardViewModel>()

    val greetingMessage = LocalDate.now().format(TimeFormatter.greetingFormatter)

    val cardBorderModifier = Modifier.border(
        BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        shape = RoundedCornerShape(5.dp)
    )

    val appBarBorderModifier = Modifier.border(
        BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    )


    Surface(modifier = Modifier.fillMaxSize()) {

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
                        todayDateInGreetingFormat = greetingMessage, modifier = cardBorderModifier
                    )

                    JobStatusCard(
                        uiState = viewModel.uiState,
                        modifier = cardBorderModifier,
                        titleTextStyle = MaterialTheme.typography.titleMedium,
                        contentTextStyle = MaterialTheme.typography.bodySmall,
                        onClick = gotoJobScreen
                    )

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
    todayDateInGreetingFormat: String,
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
                    text = todayDateInGreetingFormat, style = MaterialTheme.typography.titleSmall
                )
            }

            Image(
                painter = painterResource(id = R.drawable.vijay),
                contentDescription = stringResource(R.string.profile_picture),
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .border(2.dp, Color.LightGray, RoundedCornerShape(5.dp)),
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
            .clickable(onClick = onClick)
            .then(modifier)
            .padding(10.dp)

    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(spacing), modifier = Modifier.fillMaxWidth()
        ) {

            Text(text = stringResource(R.string.job_stats), style = titleTextStyle)

            Divider()

            StatsHeader(
                startText = stringResource(R.string.jobs, uiState.totalJobs),
                endText = stringResource(
                    R.string.of_jobs_completed, uiState.completedJobs, uiState.totalJobs
                ),
                startTextStyle = contentTextStyle,
                endTextStyle = contentTextStyle
            )

            StatsBar(
                list = uiState.jobListInfo
            )

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
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
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
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

            Text(
                text = stringResource(R.string.invoice_stats),
                modifier = Modifier.fillMaxWidth(),
                style = titleTextStyle
            )

            Divider()

            StatsHeader(
                startTextStyle = contentTextStyle,
                endTextStyle = contentTextStyle.copy(fontWeight = FontWeight.Bold),
                startText = stringResource(R.string.total_value_format, uiState.totalValue),
                endText = stringResource(R.string.collected_format, uiState.paid),
            )

            StatsBar(list = uiState.invoiceListInfo)

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()
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
                horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()
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