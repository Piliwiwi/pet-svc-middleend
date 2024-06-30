package com.example.middleend.health.model

data class VaccineScheme(
    var code: String = "",
    var name: String = "",
    var description: String = "",
    var recommendedWeek: Int? = null,
    var species: List<String> = emptyList(),
    var diseases: List<String> = emptyList(),
)