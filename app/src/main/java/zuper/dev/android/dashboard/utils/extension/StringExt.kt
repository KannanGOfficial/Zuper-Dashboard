package zuper.dev.android.dashboard.utils.extension


fun String.prefixDollar() =
    "$$this"

fun String.prefixHashtag() =
    "#$this"

fun String.suffixComma() =
    "$this, "

fun String.prefixIfen() =
    " - $this"

fun String.prefixArrow() =
    " -> $this"
