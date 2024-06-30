package com.example.middleend.pet.service

import com.example.middleend.pet.mapper.HashPetMapper
import com.example.middleend.pet.mapper.PetMapper
import com.example.middleend.pet.model.Pet
import com.example.middleend.pet.repository.PetRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import com.example.middleend.pet.dto.HashResponse

@Service
class PetService(
    private val repository: PetRepository,
    private val petMapper: PetMapper,
    private val hashPetMapper: HashPetMapper
) {

    fun getPetList(token: String, list: List<String>): ResponseEntity<List<Pet>> {
        val response = repository.getPetList(token, list)
        return if (response.statusCode.is2xxSuccessful) {
            val pets = with(petMapper) { response.body?.toPet() }
            ResponseEntity.ok(pets)
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    fun getPet(token: String, petId: String): Pet? {
        val response = repository.getPeyById(token, petId)
        return if (response.statusCode.is2xxSuccessful) {
            response.body
        } else {
            null
        }
    }

    fun addPet(token: String, userId: String, pet: Pet, profilePhoto: MultipartFile?): ResponseEntity<Any> {
        return repository.registerPet(token, userId, pet)
    }

    fun addOwnerToPet(token: String, userId: String, petId: String): ResponseEntity<Void> {
        return repository.addOwnerToPet(token, userId, petId)
    }

    fun getPetIdAndName(token: String, list: List<String>): ResponseEntity<List<HashResponse>> {
        val response = repository.getPetList(token, list)
        return if (response.statusCode.is2xxSuccessful) {
            val pets = response.body?.let { hashPetMapper.run { it.toPet() } } ?: emptyList()
            val hashResponses = hashPetMapper.run { pets.toHashResponseList() }
            ResponseEntity.ok(hashResponses)
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }
}
