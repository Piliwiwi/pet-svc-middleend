package com.example.middleend.health.model

data class Vaccine(
    var nameCode: String = "",
    var name: String = "",
    var brand: String = "",
    var laboratory: String? = null,
    var description: String? = null,
    var doses: Int = 1,
    var scheme: String? = null,
    var schedule: Schedule? = null,
    var species: List<String> = listOf(),
    var diseases: List<String> = emptyList(),
)

data class Schedule(
    var daysBetweenDose: Int? = null,
    var monthRecurrent: Int? = null
)
