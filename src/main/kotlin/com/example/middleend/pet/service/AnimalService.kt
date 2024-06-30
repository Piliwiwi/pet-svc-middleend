package com.example.middleend.pet.service

import com.example.middleend.pet.dto.PetBreedsResponse
import com.example.middleend.pet.dto.HashResponse
import com.example.middleend.pet.mapper.AllAnimalsMapper
import com.example.middleend.pet.mapper.AllBreedsMapper
import com.example.middleend.pet.model.Animal
import com.example.middleend.pet.repository.AnimalRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class AnimalService(private val repository: AnimalRepository, private val mapper: AllAnimalsMapper, private val breedMapper: AllBreedsMapper) {

    fun getAnimal(token: String, animalType: String): ResponseEntity<Animal> {
        return repository.getAnimalByType(token, animalType)
    }

    fun getAllSpecies(): ResponseEntity<List<HashResponse>> {
        val animals = repository.getAllSpecies().body ?: emptyList()
        val species = with(mapper) { animals.toResponse() }

        return if (species.isEmpty()) {
            ResponseEntity.status(HttpStatus.NO_CONTENT).body(emptyList())
        } else {
            ResponseEntity.ok(species)
        }
    }

    fun getAllBreedsByAnimalType(token: String, animalType: String): ResponseEntity<List<PetBreedsResponse>> {
        val responseEntity = repository.getAnimalByType(token, animalType)

        return if (responseEntity.statusCode.is2xxSuccessful) {
            val animal = responseEntity.body
            val breeds = animal?.breeds ?: emptyList()
            val petBreedsResponseList = with(breedMapper) { breeds.toResponse() }
            ResponseEntity.ok(petBreedsResponseList)
        } else {
            ResponseEntity.status(responseEntity.statusCode).build()
        }
    }
}