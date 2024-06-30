package com.example.middleend.utils

fun String.genderTransform() = when (this) {
    "F" -> "Hembra"
    "M" -> "Macho"
    else -> this
}