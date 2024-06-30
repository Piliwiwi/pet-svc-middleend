package com.example.middleend.pet.service

import com.example.middleend.pet.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class AuthService(@Autowired private val restTemplate: RestTemplate) {
    @Value("\${auth.api.url}")
    private lateinit var authApiUrl: String
    @Value("\${user.api.url}")
    private lateinit var userApiUrl: String

    fun validateToken(token: String): ResponseEntity<String> {
        val headers = HttpHeaders()
        headers.set("Authorization", token)
        val requestEntity = HttpEntity(null, headers)

        return try {
            restTemplate.exchange(
                "$authApiUrl/validate-token",
                HttpMethod.POST,
                requestEntity,
                String::class.java
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No se puede acceder a auth")
        }
    }

    fun getUser(token: String): ResponseEntity<User> {
        val headers = HttpHeaders()
        headers.set("Authorization", token)
        val requestEntity = HttpEntity(null, headers)

        return try {
            restTemplate.exchange(
                "$authApiUrl/user",
                HttpMethod.GET,
                requestEntity,
                User::class.java
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    fun getUsers(token: String, ids: List<String>): ResponseEntity<List<User>> {
        val headers = HttpHeaders()
        headers.set("Authorization", token)
        val requestEntity = HttpEntity(ids, headers)
        val responseType = object : ParameterizedTypeReference<List<User>>() {}

        return try {
            restTemplate.exchange(
                "$userApiUrl/users-by-ids",
                HttpMethod.POST,
                requestEntity,
                responseType
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    fun addPetToUser(userId: String, petId: String): ResponseEntity<Void> {
        val uriVariables = mapOf("userId" to userId, "petId" to petId)
        val responseEntity = restTemplate.postForEntity(
            "$userApiUrl/add-pet-to-user?userId={userId}&petId={petId}",
            null,
            Void::class.java,
            uriVariables
        )

        if (responseEntity.statusCode == HttpStatus.OK) {
            return responseEntity
        } else {
            throw InternalError("User not found")
        }
    }
}