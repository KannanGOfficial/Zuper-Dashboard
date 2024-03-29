package zuper.dev.android.dashboard.data.model

import androidx.compose.ui.graphics.Color
import zuper.dev.android.dashboard.presentation.theme.DarkMintGreen
import zuper.dev.android.dashboard.presentation.theme.LightBlue
import zuper.dev.android.dashboard.presentation.theme.LightRed
import zuper.dev.android.dashboard.presentation.theme.Yellow

data class InvoiceApiModel(
    val invoiceNumber: Int,
    val customerName: String,
    val total: Int,
    val status: InvoiceStatus
)

enum class InvoiceStatus(val color: Color) {
    Draft(Yellow),
    Pending(LightBlue),
    Paid(DarkMintGreen),
    BadDebt(LightRed)
}
