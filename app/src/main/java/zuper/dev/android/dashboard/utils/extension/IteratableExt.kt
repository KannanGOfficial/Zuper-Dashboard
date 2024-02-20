package zuper.dev.android.dashboard.utils.extension

inline fun <T> Iterable<T>.sum(selector: (T) -> Int): Long {
    var sum: Long = 0L
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

