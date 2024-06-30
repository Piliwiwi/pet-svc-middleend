package com.example.middleend.health.mapper

import com.example.middleend.health.dto.VaccineInfoPet
import com.example.middleend.pet.model.Pet
import com.example.middleend.utils.genderTransform
import org.springframework.stereotype.Component

@Component
class VaccineInfoMapper {
    fun List<Pet>.toVaccineInfo() = map { it.toVaccineInfo() }
    fun Pet.toVaccineInfo() = VaccineInfoPet(
        name = name,
        key = id,
        specieCode = animalType,
        specieName = animalName,
        photo = profilePhoto,
        nickName = nickName,
        gender = genre?.genderTransform()
    )
}