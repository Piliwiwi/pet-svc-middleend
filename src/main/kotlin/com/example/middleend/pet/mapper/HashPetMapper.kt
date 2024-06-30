package com.example.middleend.pet.mapper

import com.example.middleend.pet.dto.HashResponse
import com.example.middleend.pet.model.Pet
import org.springframework.stereotype.Component

@Component
class HashPetMapper {
    fun Map<String, Pet>.toPet(): List<Pet> = this.map { it.value.also { pet -> pet.id = it.key } }

    fun Pet.toResponse(): HashResponse = HashResponse(
        name = this.name,
        key = this.id
    )

    fun List<Pet>.toHashResponseList(): List<HashResponse> = this.map { it.toResponse() }
}