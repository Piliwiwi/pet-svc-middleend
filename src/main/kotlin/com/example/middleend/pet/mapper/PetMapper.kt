package com.example.middleend.pet.mapper

import com.example.middleend.pet.model.Pet
import org.springframework.stereotype.Component

@Component
class PetMapper {
    fun Map<String, Pet>.toPet() = this.map { it.value.also { pet -> pet.id = it.key } }
}