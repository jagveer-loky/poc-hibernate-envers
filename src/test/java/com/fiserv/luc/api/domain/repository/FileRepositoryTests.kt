package com.fiserv.luc.api.domain.repository

import com.amazonaws.SDKGlobalConfiguration
import com.fiserv.luc.api.domain.repository.brazil.IFileRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

@SpringBootTest
internal class FileRepositoryTests(@Autowired private val fileRepository: IFileRepository) {

    /**
     *
     */
    @Test
    fun `Verify if FileRepository is in Spring Container`() {
        Assertions.assertThat(fileRepository).isNotNull
    }

    /**
     *
     */
    @Test
    fun `Upload one file to 'cadastro' Amazon S3 Bucket`() {

        val file = File("test.txt")
        val fileWriter = FileWriter(file, true)
        fileWriter.write("test")
        fileWriter.close()
        fileRepository.upload("test", file)
    }
}