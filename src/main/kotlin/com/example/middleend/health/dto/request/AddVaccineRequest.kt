package com.example.middleend.health.dto.request

data class AddVaccineRequest(
    var petId: String,
    var vaccineId: String,
    var vaccinationDate: Long,
    var professionalName: String? = null,
    var clinicName: String? = null,
    var dose: Int,
)
