@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.laily.globalprint.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun Date.toStringDateMonthYear(): String {
    val formatEx = SimpleDateFormat("dd MMM yyyy", Locale.US)
    return formatEx.format(this)
}

fun String.toDate(): Date {
    var date = Date()
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS", Locale.US)
    try {
        date = format.parse(this)
    } catch (e: ParseException) {
        //DATE FAILED HANDLE
    }
    return date
}