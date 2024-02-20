package zuper.dev.android.dashboard.ui.jobs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun JobsScreen(
    navHostController: NavHostController
) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "JobsScreen",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.clickable {
                navHostController.popBackStack()
            })
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewJobsScreen() {
    JobsScreen(rememberNavController())
}