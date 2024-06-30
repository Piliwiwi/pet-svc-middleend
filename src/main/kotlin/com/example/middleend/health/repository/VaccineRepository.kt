package com.example.middleend.health.repository

import com.example.middleend.health.model.Vaccine
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate

@Repository
class VaccineRepository(@Autowired private val restTemplate: RestTemplate) {
    @Value("\${vaccine.api.url}")
    private lateinit var apiUrl: String

    fun getVaccinesBySpecie(specie: String): List<Vaccine>? {
        val responseEntity = restTemplate.exchange(
            "$apiUrl/all/${specie}",
            HttpMethod.GET,
            null,
            object : ParameterizedTypeReference<List<Vaccine>>() {}
        )

        return responseEntity.body
    }
}