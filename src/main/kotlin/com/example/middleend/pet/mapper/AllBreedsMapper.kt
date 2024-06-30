package com.example.middleend.pet.mapper

import com.example.middleend.pet.dto.PetBreedsResponse
import com.example.middleend.pet.model.Breed
import org.springframework.stereotype.Component

@Component
class AllBreedsMapper {
    fun List<Breed>.toResponse(): List<PetBreedsResponse> {
        return this.map { breed ->
            breed.toResponse()
        }
    }

    private fun Breed.toResponse() = PetBreedsResponse(
        name = this.name,
        key = this.code
    )
}