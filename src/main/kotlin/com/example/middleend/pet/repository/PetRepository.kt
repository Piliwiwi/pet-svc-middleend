package com.example.middleend.pet.repository

import com.example.middleend.pet.dto.AddPetRequest
import com.example.middleend.pet.model.Pet
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class PetRepository(@Autowired private val restTemplate: RestTemplate) {
    @Value("\${pets.api.url}")
    private lateinit var petApiUrl: String

    fun getPetList(token: String, list: List<String>): ResponseEntity<Map<String, Pet>> {
        val headers = HttpHeaders()
        headers.set("Authorization", token)
        val requestEntity = HttpEntity(list, headers)

        val responseType = object : ParameterizedTypeReference<Map<String, Pet>>() {}

        val responseEntity = restTemplate.exchange(
            "$petApiUrl/list",
            HttpMethod.POST,
            requestEntity,
            responseType
        )

        return if (responseEntity.statusCode.is2xxSuccessful) {
            val response = responseEntity.body
            ResponseEntity.ok(response)
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    fun getPeyById(token: String, petId: String): ResponseEntity<Pet> {
        val headers = HttpHeaders()
        headers.set("Authorization", token)
        val requestEntity = HttpEntity(null, headers)

        val responseEntity = restTemplate.exchange(
            "$petApiUrl/$petId",
            HttpMethod.GET,
            requestEntity,
            Pet::class.java
        )

        return responseEntity
    }

    fun registerPet(token: String, userId: String, pet: Pet): ResponseEntity<Any> {
        val headers = HttpHeaders()
        headers.set("Authorization", token)
        val request = AddPetRequest(userId = userId, pet = pet)
        val requestEntity = HttpEntity(request, headers)

        val responseEntity = restTemplate.exchange(
            "$petApiUrl/register",
            HttpMethod.POST,
            requestEntity,
            Pet::class.java
        )
        return if (responseEntity.statusCode.is2xxSuccessful) {
            val pets = responseEntity.body
            ResponseEntity.ok(pets)
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    fun addOwnerToPet(token: String, userId: String, petId: String): ResponseEntity<Void> {
        val uriVariables = mapOf("userId" to userId, "petId" to petId)
        val responseEntity = restTemplate.postForEntity(
            "$petApiUrl/add-owner-to-pet?userId={userId}&petId={petId}",
            null,
            Void::class.java,
            uriVariables
        )

        return if (responseEntity.statusCode == HttpStatus.OK) {
            responseEntity
        } else {
            ResponseEntity.internalServerError().build()
        }
    }
}