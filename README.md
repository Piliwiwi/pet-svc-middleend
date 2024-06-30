# Pet Service MiddleEnd Example

Este repositorio contiene el servicio MiddleEnd de la aplicación de ejemplo para el registro y seguimiento de la información médica de mascotas, un servicio diseñado para gestionar la comunicación entre los diferentes servicios de la aplicación.

## Requisitos

- **Java 17**: Asegúrate de tener Java 17 instalado y configurado en tu IDE (IntelliJ IDEA).
- **.env**: Debes crear un archivo `.env` con los valores necesarios para configurar los servicios.

## Configuración del Entorno

1. **Java 17**: Instala Java 17 desde [la página oficial de Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).

   - **Configuración en IntelliJ IDEA**:
     1. Abre IntelliJ IDEA y ve a `File > Project Structure`.
     2. En la sección `Project`, selecciona `Project SDK` y haz clic en `Add SDK`.
     3. Selecciona `JDK` y navega hasta el directorio donde instalaste Java 17.
     4. Aplica y guarda los cambios.

2. **Archivo .env**: Crea un archivo `.env` en el directorio raíz del proyecto con el siguiente contenido:

    ```env
    AUTH_SERVICE_URL=http://localhost:<puerto>/auth
    PET_SERVICE_URL=http://localhost:<puerto>/pet
    VACCINE_SERVICE_URL=http://localhost:<puerto>/vaccine
    ```

   - Reemplaza `<puerto>` con los puertos correspondientes para cada servicio.

## Servicios Relacionados

Este servicio MiddleEnd llama a otros servicios que se encuentran en los siguientes repositorios:

- [Servicio de Mascotas](https://github.com/Piliwiwi/pet-svc-pets-example)
- [Servicio de Autenticación](https://github.com/Piliwiwi/pet-svc-auth-example)
- Servicio de Vacunas (solo de ejemplo, no existe repositorio)

## Aplicación de Ejemplo

La aplicación móvil de ejemplo que utiliza este servicio se encuentra en el siguiente repositorio:

- [Pet App Example](https://github.com/Piliwiwi/pet-app-example)

## Ejecución

1. Clona este repositorio y navega al directorio del proyecto:

    ```bash
    git clone https://github.com/Piliwiwi/pet-svc-middleend-example.git
    cd pet-svc-middleend-example
    ```

2. Asegúrate de que los otros servicios estén corriendo en los puertos configurados en tu archivo `.env`.

3. Ejecuta el servicio MiddleEnd:

    ```bash
    ./gradlew bootRun
    ```

## Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` para más detalles.
