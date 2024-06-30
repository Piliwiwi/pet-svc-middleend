package com.example.middleend.pet.mapper

import com.example.middleend.pet.dto.HashResponse
import com.example.middleend.pet.model.Animal
import org.springframework.stereotype.Component

@Component
class AllAnimalsMapper {
    fun List<Animal>.toResponse(): List<HashResponse> {
        return this.map { animal ->
            animal.toResponse()
        }
    }

    private fun Animal.toResponse() = HashResponse(
        name = this.name,
        key = this.type
    )
}
