package com.example.middleend.pet.model

class User {
    var name: String = ""
    var email: String = ""
    var profilePhoto: String? = null
    var city: String? = null
    var region: String? = null
    var pets: List<String> = emptyList()
}
