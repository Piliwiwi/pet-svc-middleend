package com.example.middleend.health.dto

data class VaccineForSpecieDTO(
    val name: String,
    val key: String,
    val nextDose: DoseForSpecieDTO?,
    val doses: List<DoseForSpecieDTO>
)

data class DoseForSpecieDTO(
    val code: Int,
    val description: String
)
