package com.example.middleend.health.dto

data class VaccineInfoForPetDTO(
    val selectedPet: VaccineInfoPet?,
    val pets: List<VaccineInfoPet>,
    val vaccines: List<VaccineForSpecieDTO>
)

data class VaccineInfoPet(
    val name: String,
    val key: String,
    val specieCode: String,
    val specieName: String,
    val photo: String?,
    val nickName: String?,
    val gender: String?
)
