package com.example.middleend.pet.mapper

import com.example.middleend.pet.dto.HealthResponse
import com.example.middleend.pet.dto.OwnerResponse
import com.example.middleend.pet.dto.PetProfileResponse
import com.example.middleend.pet.model.Animal
import com.example.middleend.pet.model.Pet
import com.example.middleend.pet.model.User
import com.example.middleend.utils.formatDate
import com.example.middleend.utils.genderTransform
import com.example.middleend.utils.getMonths
import com.example.middleend.utils.getYears
import java.time.LocalDate
import org.springframework.stereotype.Component;

@Component
class PetProfileMapper {
    fun Pet.toProfileResponse(petId: String, animalObject: Animal?, users: List<User>): PetProfileResponse {
        val healthResponses = mutableListOf(
            HealthResponse(
                label = "Vacunas",
                icon = "ic_vaccine",
                url = "/vaccines/${petId}"
            )
        )

        return PetProfileResponse(
            name = name,
            nickName = nickName,
            gender = genre?.genderTransform() ?: "Unknown",
            breed = getBreed(breedCode, animalObject),
            animal = animalName,
            profileImage = profilePhoto,
            description = description,
            birthDate = birthDate?.formatDate(),
            age = birthDate?.describeYears(),
            health = healthResponses,
            owners = users.map { it.toOwner() }
        )
    }

    private fun User.toOwner() = OwnerResponse(
        name = name,
        photo = profilePhoto,
        location = "$city, $region"
    )

    private fun getBreed(breedCode: String?, animal: Animal?): String? {
        val breed = animal?.breeds?.find { it.code == breedCode }
        return breed?.name
    }

    private fun LocalDate.describeYears(): String {
        val years = getYears()
        val months = getMonths()
        return when {
            years > 1 -> "$years años"
            years == 1 -> "1 año"
            months > 1 -> "$months meses"
            months == 1 -> "1 mes"
            else -> "Recién nacido"
        }
    }
}