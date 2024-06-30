package com.example.middleend.pet.dto

import com.example.middleend.pet.model.Pet

data class AddPetRequest(
    val userId: String,
    val pet: Pet
)