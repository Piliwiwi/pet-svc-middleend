package com.example.middleend.pet.model

data class Animal(
    val type: String = "",
    val name: String = "",

    /** Relations */
    var breeds: List<Breed> = emptyList()
)