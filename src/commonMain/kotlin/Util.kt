import platform.posix._SC_NPROCESSORS_ONLN
import platform.posix.sysconf

fun List<List<String>>.tableFormat(hasHeader: Boolean = false): String {
    val columnCount = maxOf { it.size }
    val columnSizes = (0 until columnCount).map { i ->
        this.mapNotNull { it.getOrNull(i) }.maxOf { it.length } + 2
    }
    return buildString {
        this@tableFormat.forEach { row ->
            row.forEachIndexed { i, word ->
                val startPadding = (columnSizes[i] - word.length) / 2
                val endPadding = columnSizes[i] - word.length - startPadding
                append(" ".repeat(startPadding))
                append(word)
                append(" ".repeat(endPadding))
                if (i != row.size - 1) append("|")
            }
            appendLine()
            if (hasHeader && row === this@tableFormat.first()) {
                appendLine("-".repeat(columnSizes.sum() + columnCount - 1))
            }
        }
    }
}

fun Int.pow(power: Int): Int {
    var result = 1
    repeat(power) { result *= this@pow }
    return result
}

fun cpuCount(): Int = sysconf(_SC_NPROCESSORS_ONLN).toInt()
