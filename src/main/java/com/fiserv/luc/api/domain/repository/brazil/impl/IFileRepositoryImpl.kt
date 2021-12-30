package com.fiserv.luc.api.domain.repository.brazil.impl

import com.amazonaws.AmazonServiceException
import com.amazonaws.services.s3.AmazonS3
import com.fiserv.luc.api.domain.repository.brazil.IFileRepository
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import java.io.ByteArrayInputStream
import java.io.File
import java.io.IOException
import java.io.InputStream

@Repository
open class IFileRepositoryImpl(private val amazonS3: AmazonS3) : IFileRepository {

    @Value("\${cloud.aws.s3.bucketName}")
    private var bucket: String? = "cadastro";

    override fun upload(path: String, file: File) {
        amazonS3.putObject(bucket, path, file)
    }

    override fun upload(path: String, bytes: ByteArray) {
        this.upload(path, ByteArrayInputStream(bytes))
    }

    override fun upload(path: String, inputStream: InputStream) {
        try {
            amazonS3.putObject(bucket, path, inputStream, null)
        } catch (e: AmazonServiceException) {
            throw IllegalStateException("Failed to upload the file", e)
        }
    }


    override fun download(path: String): ByteArray {
        return try {
            val `object` = amazonS3.getObject(bucket, path)
            val objectContent = `object`.objectContent
            IOUtils.toByteArray(objectContent)
        } catch (e: AmazonServiceException) {
            throw IllegalStateException("Failed to download the file", e)
        } catch (e: IOException) {
            throw IllegalStateException("Failed to download the file", e)
        }
    }

}