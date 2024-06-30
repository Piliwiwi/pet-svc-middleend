package com.example.middleend.health.service

import com.example.middleend.health.dto.VaccineForSpecieDTO
import com.example.middleend.health.dto.VaccineInfoForPetDTO
import com.example.middleend.health.mapper.VaccineForSpecieMapper
import com.example.middleend.health.mapper.VaccineInfoMapper
import com.example.middleend.health.repository.VaccinationRepository
import com.example.middleend.health.repository.VaccineRepository
import com.example.middleend.pet.mapper.PetMapper
import com.example.middleend.pet.model.Pet
import com.example.middleend.pet.repository.AnimalRepository
import com.example.middleend.pet.repository.PetRepository
import com.example.middleend.pet.service.AuthService
import org.springframework.stereotype.Service

@Service
class VaccineService(
    private val vaccineRepository: VaccineRepository,
    private val vaccinationRepository: VaccinationRepository,
    private val vaccineSpecieMapper: VaccineForSpecieMapper,
    private val petRepository: PetRepository,
    private val animalRepository: AnimalRepository,
    private val authService: AuthService,
    private val vaccineInfoMapper: VaccineInfoMapper,
    private val petMapper: PetMapper
) {

    fun getVaccinesBySpecie(petId: String, specie: String): List<VaccineForSpecieDTO> {
        val lastVaccines = vaccinationRepository.getAllLastVaccinationFromPet(petId)
        val allVaccines = vaccineRepository.getVaccinesBySpecie(specie)

        val vaccinesForSpecie = with(vaccineSpecieMapper) {
            allVaccines?.toVaccineForSpecie(lastVaccines.orEmpty().toMutableList())
        }
        return vaccinesForSpecie.orEmpty()
    }

    fun getVaccineAndPetInfo(petId: String?, token: String): VaccineInfoForPetDTO? {
        val user = authService.getUser(token).body
        val petIds = user?.pets.orEmpty()
        var selectedPet: Pet? = null
        var vaccines: List<VaccineForSpecieDTO>? = null
        if (petId != null) {
            selectedPet = petRepository.getPeyById(token, petId).body
            vaccines = getVaccinesBySpecie(petId, selectedPet?.animalType.orEmpty())
        }
        val pets = with(petMapper) { petRepository.getPetList(token, petIds).body?.toPet() }
            ?.map { pet ->
                val animal = animalRepository.getAnimalByType(token, pet.animalType).body
                if (animal != null) {
                    pet.breed = animal.breeds.firstOrNull { it.code == pet.breedCode }
                    animal.breeds = emptyList()
                    pet.animal = animal
                }
                pet
            }
        return with(vaccineInfoMapper) {
            VaccineInfoForPetDTO(
                selectedPet = selectedPet?.toVaccineInfo(),
                pets = pets?.toVaccineInfo().orEmpty(),
                vaccines = vaccines.orEmpty()
            )
        }
    }
}