package com.example.middleend.pet.dto

data class PetProfileResponse(
    val name : String,
    val gender : String,
    val breed : String?,
    val animal : String,
    val birthDate : String?,
    val age : String?,
    val nickName : String?,
    val profileImage : String?,
    val description : String?,
    val health : MutableList<HealthResponse>,
    val owners: List<OwnerResponse>
)

data class HealthResponse(
    val label : String,
    val icon : String,
    val url: String
)

data class OwnerResponse(
    val photo: String?,
    val name: String,
    val location: String
)