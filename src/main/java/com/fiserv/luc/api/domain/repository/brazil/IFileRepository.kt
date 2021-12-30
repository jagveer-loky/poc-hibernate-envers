package com.fiserv.luc.api.domain.repository.brazil

import java.io.File
import java.io.InputStream

interface IFileRepository {

    fun upload(path: String, file: File)

    fun upload(path: String, bytes: ByteArray)

    fun upload(path: String, inputStream: InputStream)

    fun download(key: String): ByteArray
}