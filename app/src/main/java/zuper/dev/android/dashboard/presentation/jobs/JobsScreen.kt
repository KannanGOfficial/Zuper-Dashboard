package zuper.dev.android.dashboard.presentation.jobs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import zuper.dev.android.dashboard.R
import zuper.dev.android.dashboard.presentation.dashboard.StatsHeader
import zuper.dev.android.dashboard.presentation.dashboard.StatsBar
import zuper.dev.android.dashboard.utils.extension.prefixHashtag

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun JobsScreen(onBackClick: () -> Unit) {

    val viewModel = hiltViewModel<JobsViewModel>()

    val appBarBorderModifier = Modifier.border(
        BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    )

    val cardBorderModifier = Modifier.border(
        BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        shape = RoundedCornerShape(5.dp)
    )

    val tabItems = listOf(
        stringResource(
            R.string.progress_format,
            stringResource(R.string.yet_to_start),
            viewModel.uiState.yetToStartJobList.size
        ), stringResource(
            R.string.progress_format,
            stringResource(id = R.string.progress_in),
            viewModel.uiState.inProgressJobList.size
        ), stringResource(
            R.string.progress_format,
            stringResource(id = R.string.cancelled),
            viewModel.uiState.cancelledJobList.size
        ), stringResource(
            R.string.progress_format,
            stringResource(id = R.string.completed),
            viewModel.uiState.completedJobList.size
        ), stringResource(
            R.string.progress_format,
            stringResource(id = R.string.in_completed),
            viewModel.uiState.inCompleteJobList.size
        )
    )


    val pagerState = rememberPagerState {
        tabItems.size
    }

    LaunchedEffect(viewModel.uiState.selectedTabIndex) {
        pagerState.animateScrollToPage(viewModel.uiState.selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) viewModel.updateSelectedTabIndex(pagerState.currentPage)
    }


    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()) {

            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "back_icon")
                    }
                },
                title = {
                    Text(
                        text = stringResource(R.string.jobs_format, viewModel.uiState.totalJob),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                modifier = appBarBorderModifier,
            )

            StatsHeader(
                startText = stringResource(R.string.jobs, viewModel.uiState.totalJob),
                endText = stringResource(
                    R.string.of_jobs_completed,
                    viewModel.uiState.completedJobList.size,
                    viewModel.uiState.totalJob
                ), startTextStyle = MaterialTheme.typography.bodySmall,
                endTextStyle = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)
            )

            StatsBar(
                list = viewModel.uiState.jobListInfo,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
            )

            Divider()

            ScrollableTabRow(
                selectedTabIndex = viewModel.uiState.selectedTabIndex,
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                edgePadding = 0.dp,

                ) {
                tabItems.forEachIndexed { index, name ->
                    Tab(selected = index == viewModel.uiState.selectedTabIndex, onClick = {
                        viewModel.updateSelectedTabIndex(index)
                    }, text = {
                        if (viewModel.uiState.selectedTabIndex == index)
                            Text(
                                text = name,
                                fontWeight = FontWeight.Bold,
                            )
                        else
                            Text(
                                text = name,
                                fontWeight = FontWeight.Medium,
                            )
                    })
                }
            }

            HorizontalPager(
                state = pagerState, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { selectedIndex ->

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp), verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(viewModel.getJobList(selectedIndex)) {
                        JobItem(
                            modifier = cardBorderModifier,
                            job = it,
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun JobItem(
    modifier: Modifier,
    job: Job
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .padding(10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Text(
                text = job.jobNumber.toString().prefixHashtag(),
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
            Text(
                text = job.title,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = job.jobTimeDetails,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }
    }
}
