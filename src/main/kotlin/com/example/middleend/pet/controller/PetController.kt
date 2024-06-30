package com.example.middleend.pet.controller

import com.example.middleend.pet.dto.HashResponse
import com.example.middleend.pet.dto.PetProfileResponse
import com.example.middleend.pet.dto.error.Message
import com.example.middleend.pet.mapper.PetProfileMapper
import com.example.middleend.pet.model.Pet
import com.example.middleend.pet.service.AnimalService
import com.example.middleend.pet.service.AuthService
import com.example.middleend.pet.service.PetService
import com.example.middleend.pet.service.S3Service
import com.example.middleend.pet.service.S3Service.Companion.S3Folder.PET_PROFILE_FOLDER
import com.example.middleend.utils.toLocalDate
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/frontend")
class PetController(
    private val petService: PetService,
    private val authService: AuthService,
    private val animalService: AnimalService,
    private val s3Service: S3Service,
    private val petProfileMapper: PetProfileMapper
) {
    @GetMapping("/pets")
    fun getPetsByUser(
        @RequestHeader("Authorization") authorization: String?,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        try {
            val user = authService.getUser(authorization.orEmpty()).body
            val petIds = user?.pets.orEmpty()

            val pets = petService.getPetList(authorization.orEmpty(), petIds).body
                ?.map { pet ->
                    val animal = animalService.getAnimal(authorization.orEmpty(), pet.animalType).body
                    if (animal != null) {
                        pet.breed = animal.breeds.firstOrNull { it.code == pet.breedCode }
                        animal.breeds = emptyList()
                        pet.animal = animal
                    }
                    pet
                }
            return ResponseEntity.ok(pets)
        } catch (e: Exception) {
            return ResponseEntity.internalServerError().build()
        }
    }

    @GetMapping("/pet/profile/{pet-id}")
    fun getPetProfile(
        @RequestHeader("Authorization") authorization: String?,
        @PathVariable("pet-id") petId: String,
    ): ResponseEntity<PetProfileResponse> {
        return try {
            val pet = petService.getPet(petId = petId, token = authorization.orEmpty())
            if (pet != null) {
                val animal = animalService.getAnimal(authorization.orEmpty(), pet.animalType)
                val users = authService.getUsers(authorization.orEmpty(), pet.ownerIds)
                val petProfile = with(petProfileMapper) {
                    pet.toProfileResponse(petId, animal.body, users.body.orEmpty())
                }
                ResponseEntity.status(200).body(petProfile)
            } else {
                ResponseEntity.noContent().build()
            }
        } catch (e: Exception) {
            ResponseEntity.internalServerError().build()
        }
    }

    @PostMapping("/pet/add")
    fun addPet(
        @RequestHeader("Authorization") authorization: String?,
        request: HttpServletRequest,
        @RequestPart("body") body: Pet,
        @RequestPart("profilePhotoFile", required = false) profilePhoto: MultipartFile?
    ): ResponseEntity<Any> {
        try {
            (request.getAttribute("authenticatedUser") as? String)?.let { userId ->
                val petUrl = profilePhoto?.let { photo ->
                    s3Service.createAndUploadFile(photo, PET_PROFILE_FOLDER)
                }
                body.profilePhoto = petUrl
                body.birthDate = body.birthDateMillis?.toLocalDate()
                return petService.addPet(authorization.orEmpty(), userId, body, profilePhoto)
            }
            return ResponseEntity.status(403).body(Message("No autorizado"))
        } catch (e: Exception) {
            return ResponseEntity.internalServerError().build()
        }
    }

    @GetMapping("/animals/all")
    fun getAllSpecies(): ResponseEntity<*> {
        return try {
            animalService.getAllSpecies()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }

    @GetMapping("/breeds/{animalType}")
    fun getAllBreedsByAnimalType(
        @RequestHeader("Authorization") authorization: String?,
        @PathVariable("animalType") animalType: String
    ): ResponseEntity<*> {
        return try {
            animalService.getAllBreedsByAnimalType(token = authorization.orEmpty(), animalType)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }

    @GetMapping("/pets/list")
    fun getPetIdAndName(
        @RequestHeader("Authorization") authorization: String?
    ): ResponseEntity<List<HashResponse>> {
        return try {
            val user = authService.getUser(authorization.orEmpty()).body
            val petIds = user?.pets.orEmpty()
            val petsResponse = petService.getPetIdAndName(authorization.orEmpty(), petIds).body
            ResponseEntity.ok(petsResponse)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(emptyList())
        }
    }

    /** Internal */
    @PostMapping("/pet/add/{petId}")
    fun addPetOwner(
        @RequestHeader("Authorization") authorization: String?,
        request: HttpServletRequest,
        @PathVariable("petId") petId: String
    ): ResponseEntity<*> {
        try {
            (request.getAttribute("authenticatedUser") as? String)?.let { userId ->
                petService.addOwnerToPet(authorization.orEmpty(), userId, petId)
                authService.addPetToUser(userId, petId)
                return ResponseEntity.ok(Message("Ok"))
            }
            return ResponseEntity.status(403).body(Message("No autorizado"))
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }
}
