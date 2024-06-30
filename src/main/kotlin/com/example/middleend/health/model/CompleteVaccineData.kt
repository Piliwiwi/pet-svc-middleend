package com.example.middleend.health.model

data class CompleteVaccineData(
    val vaccination: Vaccination,
    val vaccine: Vaccine?,
    val vaccineScheme: VaccineScheme?
)
