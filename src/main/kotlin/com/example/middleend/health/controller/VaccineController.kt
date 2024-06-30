package com.example.middleend.health.controller

import com.example.middleend.health.service.VaccineService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/frontend")
class VaccineController(
    private val vaccineService: VaccineService
) {
    @GetMapping("/vaccines")
    fun getAllVaccinesBySpecie(
        @RequestParam("petId") petId: String,
        @RequestParam("specie") specie: String
    ): ResponseEntity<Any> {
        return try {
            val vaccines = vaccineService.getVaccinesBySpecie(petId, specie)
            ResponseEntity.status(200).body(vaccines)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
        }
    }

    @GetMapping("/vaccine-info")
    fun getVaccineInfo(
        @RequestParam("petId") petId: String?,
        @RequestHeader("Authorization") authorization: String?,
    ): ResponseEntity<Any> {
        return try {
            val vaccineInfo = vaccineService.getVaccineAndPetInfo(petId, authorization.orEmpty())
            ResponseEntity.status(200).body(vaccineInfo)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
        }
    }
}