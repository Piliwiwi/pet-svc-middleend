package com.example.middleend.pet.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class S3Service(private val amazonS3: AmazonS3, @Value("\${s3.bucket}") private val bucketName: String) {

    fun createAndUploadFile(profilePhoto: MultipartFile, dir: S3Folder): String? {
        return try {
            val fileName = "IMG_${System.currentTimeMillis()}_${profilePhoto.originalFilename}"
            val key = "${dir.dir}/$fileName"
            val putObjectRequest = PutObjectRequest(bucketName, key, profilePhoto.inputStream, ObjectMetadata())
            amazonS3.putObject(putObjectRequest)
            amazonS3.getUrl(bucketName, key).toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    companion object {
        enum class S3Folder(val dir: String) {
            PET_PROFILE_FOLDER("pet-profile")
        }
    }
}