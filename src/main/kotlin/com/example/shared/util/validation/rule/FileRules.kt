package com.example.shared.util.validation.rule

import com.example.shared.util.validation.violation.FileViolation
import io.github.kverify.core.context.validate
import io.github.kverify.core.model.Rule
import java.io.File

object FileRules {
    val exists =
        Rule<File> { file ->
            validate(file.exists()) {
                FileViolation.FileNotFound(
                    file,
                )
            }
        }

    fun matchesExtension(extension: String) =
        Rule<File> { file ->
            validate(file.extension == extension) {
                FileViolation.IncorrectExtension(
                    file,
                    extension,
                )
            }
        }
}
