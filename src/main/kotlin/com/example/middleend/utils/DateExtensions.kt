package com.example.middleend.utils

import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale


fun LocalDate.formatDate(): String {
    val formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'del' yyyy", Locale("es", "ES"))
    return format(formatter)
}

fun LocalDate.formatDateWithoutYear(): String {
    val formatter = DateTimeFormatter.ofPattern("d 'de' MMMM", Locale("es", "ES"))
    return format(formatter)
}

fun LocalDate.slashFormatDate(): String {
    val formatter = DateTimeFormatter.ofPattern("dd'/'MM'/'yyyy", Locale("es", "ES"))
    return format(formatter)
}

fun LocalDate.getYears(): Int {
    val currentDate = LocalDate.now()
    val period = Period.between(this, currentDate)
    return period.years
}

fun LocalDate.getMonths(): Int {
    val currentDate = LocalDate.now()
    val period = Period.between(this, currentDate)
    return period.years * 12 + period.months
}

fun Long.toLocalDate(): LocalDate? {
    val instant: Instant = Instant.ofEpochMilli(this)
    return instant.atZone(ZoneId.systemDefault()).toLocalDate()
}