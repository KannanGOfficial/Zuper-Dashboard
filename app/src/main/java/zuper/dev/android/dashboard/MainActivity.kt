package zuper.dev.android.dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import zuper.dev.android.dashboard.presentation.theme.AppTheme
import zuper.dev.android.dashboard.utils.navigation.SetupNavGraph
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            AppTheme {
                val navHostController = rememberNavController()

                SetupNavGraph(navHostController = navHostController)
            }
        }
    }
}


