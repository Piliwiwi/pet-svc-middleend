package com.example.middleend.health.repository

import com.example.middleend.health.model.CompleteVaccineData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class VaccinationRepository(@Autowired private val restTemplate: RestTemplate) {
    @Value("\${vaccination.api.url}")
    private lateinit var apiUrl: String

    fun getAllLastVaccinationFromPet(petId: String): List<CompleteVaccineData>? {
        val responseEntity = restTemplate.exchange(
            "$apiUrl/all-last-scheme/$petId",
            HttpMethod.GET,
            null,
            object : ParameterizedTypeReference<List<CompleteVaccineData>>() {}
        )

        return responseEntity.body
    }
}