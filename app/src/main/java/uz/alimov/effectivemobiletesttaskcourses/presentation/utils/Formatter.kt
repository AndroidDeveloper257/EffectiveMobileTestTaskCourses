package uz.alimov.effectivemobiletesttaskcourses.presentation.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(dateString: String?): String {
    if (dateString.isNullOrBlank()) return ""
    return try {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru"))
        val date = LocalDate.parse(dateString, inputFormatter)
        val formatted = date.format(outputFormatter)
        val parts = formatted.split(" ")
        if (parts.size < 3) return formatted
        val capitalizedMonth = parts[1].replaceFirstChar { it.titlecase(Locale("ru")) }
        "${parts[0]} $capitalizedMonth ${parts[2]}"
    } catch (_: Exception) {
        ""
    }
}