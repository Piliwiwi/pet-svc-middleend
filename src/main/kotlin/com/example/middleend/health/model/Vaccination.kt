package com.example.middleend.health.model

import java.time.LocalDate

data class Vaccination(
    var petId: String = "",
    var vaccineTypeCode: String = "",
    var dose: Int? = null,
    var applicationDate: LocalDate? = null,
    var stampPhoto: String? = null,
    var vetName: String? = null,
    var vetClinic: String? = null,
)