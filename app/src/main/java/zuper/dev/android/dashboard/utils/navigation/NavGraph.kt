package zuper.dev.android.dashboard.utils.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import zuper.dev.android.dashboard.ui.dashboard.DashBoardScreen
import zuper.dev.android.dashboard.ui.jobs.JobsScreen

@Composable
fun SetupNavGraph(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = Screens.startDestination) {

        composable(
            route = Screens.DASH_BOARD_SCREEN.route
        ) {
            DashBoardScreen(navHostController)
        }

        composable(
            route = Screens.JOBS_SCREEN.route
        ) {
            JobsScreen(navHostController)
        }
    }

}