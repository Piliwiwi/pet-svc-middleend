package com.example.middleend

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import io.github.cdimascio.dotenv.Dotenv

@SpringBootTest
class MiddleEndApplicationTests {

	init {
        // Cargar variables de entorno desde el archivo .env
        val dotenv = Dotenv.load()
        dotenv.entries().forEach { entry -> 
            System.setProperty(entry.key, entry.value)
        }
    }
	
	@Test
	fun contextLoads() {
	}

}
