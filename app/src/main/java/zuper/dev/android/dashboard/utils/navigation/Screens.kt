package zuper.dev.android.dashboard.utils.navigation

enum class Screens(val route: String) {
    DASH_BOARD_SCREEN("dash_board_screen"),
    JOBS_SCREEN("jobs_screen");

    companion object{
        val startDestination = DASH_BOARD_SCREEN.route
    }
}