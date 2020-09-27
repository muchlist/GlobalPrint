package com.laily.globalprint.utils

import java.text.NumberFormat
import java.util.*

class TransformInt {
    fun toRupiahString(angka: Int): String{
        val localeID = Locale("in", "ID")
        val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)

        return formatRupiah.format(angka)
    }
}