package com.example.middleend

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AwsConfiguration {

    @Value("\${aws.accessKeyId}")
    private lateinit var accessKeyId: String

    @Value("\${aws.secretKey}")
    private lateinit var secretKey: String

    @Bean
    fun amazonS3(): AmazonS3 {
        val credentials = BasicAWSCredentials(accessKeyId, secretKey)
        return AmazonS3ClientBuilder.standard()
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.US_EAST_2)
            .build()
    }
}