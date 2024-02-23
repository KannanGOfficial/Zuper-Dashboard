package zuper.dev.android.dashboard.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import zuper.dev.android.dashboard.R

@Composable
fun JobHeader(
    totalJobs: Int,
    completedJobs: Int,
    modifier: Modifier = Modifier,
    contentTextStyle: TextStyle
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stringResource(R.string.jobs, totalJobs), style = contentTextStyle)
        Text(
            text = stringResource(
                R.string.of_jobs_completed,
                completedJobs,
                totalJobs
            ),
            style = contentTextStyle
        )
    }
}