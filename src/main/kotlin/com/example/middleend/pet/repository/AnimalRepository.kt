package com.example.middleend.pet.repository

import com.example.middleend.pet.model.Animal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class AnimalRepository(@Autowired private val restTemplate: RestTemplate) {
    @Value("\${pets.api.url}")
    private lateinit var petApiUrl: String

    fun getAnimalByType(token: String, animalType: String): ResponseEntity<Animal> {
        val responseEntity = restTemplate.exchange(
            "$petApiUrl/animal/$animalType",
            HttpMethod.GET,
            null,
            Animal::class.java
        )

        return if (responseEntity.statusCode.is2xxSuccessful) {
            val pets = responseEntity.body
            ResponseEntity.ok(pets)
        } else {
            ResponseEntity.status(responseEntity.statusCode).build()
        }
    }
    
    fun getAllSpecies(): ResponseEntity<List<Animal>> {
        val responseEntity = restTemplate.exchange(
                "$petApiUrl/animals",
                HttpMethod.GET,
                null,
                object : ParameterizedTypeReference<List<Animal>>() {}
        )

        return if (responseEntity.statusCode.is2xxSuccessful) {
            val species = responseEntity.body ?: emptyList()
            ResponseEntity.ok(species)
        } else {
            ResponseEntity.status(responseEntity.statusCode).build()
        }
    }
}