package uz.gita.dictionary_mehriddinnnnnnnnnnn.utils

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import uz.gita.dictionary_mehriddinnnnnnnnnnn.App
import uz.gita.dictionary_mehriddinnnnnnnnnnn.R


fun  String.spannable(query: String): SpannableString {
    val span = SpannableString(this)

    val color = ForegroundColorSpan(ContextCompat.getColor(App.context, R.color.teal_200))
    val startIndex = this.indexOf(query, 0, true)
    if (startIndex > -1) {
        span.setSpan(color, startIndex, startIndex + query.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    return span
}

fun  String.spannableReset(query: String): SpannableString {
    val span = SpannableString(this)

    val color = ForegroundColorSpan(ContextCompat.getColor(App.context, R.color.black))
    val startIndex = this.indexOf(query, 0, true)
    if (startIndex > -1) {
        span.setSpan(color, startIndex, startIndex + query.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    return span
}