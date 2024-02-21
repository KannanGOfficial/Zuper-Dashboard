package zuper.dev.android.dashboard.ui.jobs

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import zuper.dev.android.dashboard.R
import zuper.dev.android.dashboard.data.DataRepository
import zuper.dev.android.dashboard.data.remote.ApiDataSource
import zuper.dev.android.dashboard.utils.extension.prefixHashtag

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun JobsScreen(
    navHostController: NavHostController
) {
    val apiDataSource = ApiDataSource()
    val dataRepository = DataRepository(apiDataSource)
    val viewModel = viewModel<JobsViewModel> {
        JobsViewModel(dataRepository)
    }

    val appBarBorderModifier = Modifier.border(
        BorderStroke(1.dp, Color.LightGray)
    )

    val cardBorderModifier = Modifier.border(
        BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(5.dp)
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

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    val pagerState = rememberPagerState {
        tabItems.size
    }

    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) selectedTabIndex = pagerState.currentPage
    }


    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()) {

            TopAppBar(navigationIcon = {
                IconButton(onClick = { navHostController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "back_icon")
                }
            },title = {
                Text(
                    text = "Jobs (${viewModel.uiState.totalJob})",
                    style = MaterialTheme.typography.titleLarge
                )
            }, modifier = appBarBorderModifier,
                )

            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                tabItems.forEachIndexed { index, name ->
                    Tab(selected = index == selectedTabIndex, onClick = {
                        selectedTabIndex = index
                    }, text = {
                        Text(text = name)
                    })
                }
            }

            HorizontalPager(
                state = pagerState, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { selectedIndex ->

                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp), verticalArrangement = Arrangement.spacedBy(20.dp)){
                    items(viewModel.getJobList(selectedIndex)) {
                        JobItem(modifier = cardBorderModifier,
                            jobNumber = it.jobNumber.toString().prefixHashtag(),
                            jobTitle = it.title)
                    }
                }
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = tabItems[selectedIndex])
                }
            }
        }
    }
}


@Composable
fun JobItem(
    modifier: Modifier,
    jobNumber: String = "#121",
    jobTitle: String = "Interior design",
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .padding(10.dp)
    ) {
        Column {
            Text(text = jobNumber, modifier = Modifier.fillMaxWidth())
            Text(text = jobTitle, modifier = Modifier.fillMaxWidth())
            Text(text = "Today, 10.30 - 11.00 AM", modifier = Modifier.fillMaxWidth())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewJobsScreen() {
//    JobsScreen(rememberNavController())
    val cardBorderModifier = Modifier.border(
        BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(5.dp)
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp), contentAlignment = Alignment.Center
    ) {
        JobItem(
            modifier = cardBorderModifier
        )
    }
}