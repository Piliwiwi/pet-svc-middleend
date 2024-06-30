package com.example.middleend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class MiddleEndApplication

fun main(args: Array<String>) {
    configureEnv()
    runApplication<MiddleEndApplication>(*args)
}

private fun configureEnv() {
    val dotenv = Dotenv.load()
    dotenv.entries().forEach { entry ->
        System.setProperty(entry.key, entry.value)
    }
}
