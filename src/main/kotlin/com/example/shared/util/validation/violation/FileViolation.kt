package com.example.shared.util.validation.violation

import com.example.shared.util.serialization.SerializedUUID
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement
import java.io.File

@Serializable
sealed class FileViolation(
    override val code: String,
) : PropertyViolation {
    constructor(
        fileCode: FileCode,
    ) : this(fileCode.name)

    override fun toJsonElement(json: Json): JsonElement = json.encodeToJsonElement(this)

    @Serializable
    data class NoFileProvided(
        override val message: String = "No file provided",
    ) : FileViolation(FileCode.NO_FILE_PROVIDED)

    @Serializable
    data class FileNotFound(
        val fileId: SerializedUUID? = null,
        override val message: String = fileNotFoundMessage(fileId),
    ) : FileViolation(FileCode.FILE_NOT_FOUND) {
        constructor(
            file: File,
        ) : this(
            message = "File '${file.absolutePath}' not found",
        )
    }

    @Serializable
    data class IncorrectExtension(
        val path: String,
        val extension: String,
        override val message: String = "File '$path' extension must have '$extension' extension",
    ) : FileViolation(FileCode.INCORRECT_EXTENSION) {
        constructor(
            file: File,
            extension: String,
        ) : this(
            path = file.absolutePath,
            extension = extension,
        )
    }

    @Serializable
    data class FileNameNotProvided(
        override val message: String = "File name not provided",
    ) : FileViolation(FileCode.FILE_NAME_NOT_PROVIDED)

    enum class FileCode {
        FILE_NOT_FOUND,
        INCORRECT_EXTENSION,
        NO_FILE_PROVIDED,
        FILE_NAME_NOT_PROVIDED,
    }
}

private fun fileNotFoundMessage(fileId: SerializedUUID?): String =
    if (fileId != null) {
        "File with id='$fileId' was not found"
    } else {
        "File was not found"
    }
