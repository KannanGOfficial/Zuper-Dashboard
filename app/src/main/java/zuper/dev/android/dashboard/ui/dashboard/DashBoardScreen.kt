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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import zuper.dev.android.dashboard.utils.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardScreen(
    navHostController: NavHostController
) {
    val apiDataSource = ApiDataSource()
    val dataRepository = DataRepository(apiDataSource)
    val viewModel = viewModel<DashboardViewModel>() {
        DashboardViewModel(dataRepository)
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Column {

            TopAppBar(title = { Text(text = "DashBoard") })


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {

                    GreetingCard()

                    JobStatusCard(uiState = viewModel.uiState) {
                        navHostController.navigate(Screens.JOBS_SCREEN.route)
                    }
                    InvoiceStatusCard(uiState = viewModel.uiState)
                }
            }
        }
    }
}

@Composable
fun GreetingCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(5.dp))
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
                Text(text = "Hello, Henry Jones !\uD83D\uDC4B")
                Text(text = "Friday, January 6th 2024")
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

@Preview(showBackground = true)
@Composable
fun PreviewDashBoardScreen() {
//    DashBoardScreen(rememberNavController())
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp), contentAlignment = Alignment.Center
    ) {
//        JobStatusCard()
//        StatusText(progressText = "Yet to Start", progress = 10, progressColor = Color.Black)
//        InvoiceStatusCard()
        GreetingCard()
    }
}

@Composable
fun JobStatusCard(
    uiState: DashBoardUiState,
    onClick: () -> Unit
) {

    val spacing = 10.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(5.dp))
            .padding(10.dp)
            .clickable {
                onClick()
            }
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(spacing),
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(text = "Job Stats")

            Divider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${uiState.totalJobs} Jobs")
                Text(text = "${uiState.completedJobs} of ${uiState.totalJobs} Jobs completed")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatusText(
                    progressText = "Yet to Start",
                    progress = uiState.yetToStart.toString(),
                    progressColor = LightPurple
                )
                StatusText(
                    progressText = "In-Progress",
                    progress = uiState.inProgress.toString(),
                    progressColor = LightBlue
                )
//                Text(text = "In-Progress (${uiState.inProgress})")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatusText(
                    progressText = "cancelled",
                    progress = uiState.cancelled.toString(),
                    progressColor = Yellow
                )
                StatusText(
                    progressText = "Completed",
                    progress = uiState.completedJobs.toString(),
                    progressColor = DarkMintGreen
                )
            }


            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                StatusText(
                    progressText = "In-Completed",
                    progress = uiState.inCompleted.toString(),
                    progressColor = LightRed
                )
            }
        }

    }
}

@Composable
fun StatusText(
    progressText: String,
    progress: String,
    progressColor: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .width(15.dp)
                .height(15.dp)
                .background(color = progressColor)
        )
        Text(text = "$progressText ($progress)")
    }

}

@Composable
fun InvoiceStatusCard(uiState: DashBoardUiState = DashBoardUiState()) {
    val spacing = 10.dp
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(5.dp))
            .padding(10.dp),
    ) {

        Column(verticalArrangement = Arrangement.spacedBy(spacing)) {

            Text(text = "Invoice Stats", modifier = Modifier.fillMaxWidth())

            Divider()

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Total Value ($${uiState.totalValue})")

                Text(text = "Collected ($${uiState.paid})")
            }

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                StatusText(
                    progressText = "Draft",
                    progress = uiState.draft.toString().suffixDollar(),
                    progressColor = Yellow
                )

                StatusText(
                    progressText = "Pending",
                    progress = uiState.pending.toString().suffixDollar(),
                    progressColor = LightBlue
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                StatusText(
                    progressText = "Paid",
                    progress = uiState.paid.toString().suffixDollar(),
                    progressColor = DarkMintGreen
                )

                StatusText(
                    progressText = "Bad Debit",
                    progress = uiState.badDebit.toString().suffixDollar(),
                    progressColor = LightRed
                )
            }

        }
    }


}

fun String.suffixDollar() =
    "$" + this