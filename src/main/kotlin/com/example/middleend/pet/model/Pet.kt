package com.example.middleend.pet.model

import java.time.LocalDate

class Pet {
    var id: String = ""
    var name: String = ""
    var breedCode: String? = null
    var breed: Breed? = null
    var animalName: String = ""
    var genre: String? = null
    var description: String? = null
    var birthDate: LocalDate? = null
    var birthDateMillis: Long? = null
    var nickName: String? = null
    var profilePhoto: String? = null
    var ownerIds: List<String> = emptyList()

    /** Scheme Relations */
    var animalType: String = ""
    var animal: Animal = Animal()
}