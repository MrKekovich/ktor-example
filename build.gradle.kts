import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

// !! All plugins are located in the "gradle/libs.versions.toml" file under [plugins] !!
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktor)
    alias(libs.plugins.shadow.jar)
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

// !! All dependencies are located in the "gradle/libs.versions.toml" file under [libraries] !!
dependencies {
    // Kotlin specific libraries
    implementation(libs.kotlin.datetime)

    // Ktor Core and Server
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.host.common)
    implementation(libs.ktor.server.netty)

    // Ktor Client
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.apache)
    implementation(libs.ktor.client.content.negotiation)

    // Ktor Features
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.status.pages)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.websockets)
    implementation(libs.ktor.server.webjars)
    implementation(libs.ktor.server.request.validation)

    // Schema Kenerator
    implementation(libs.schema.kenerator.core)
    implementation(libs.schema.kenerator.swagger)
    implementation(libs.schema.kenerator.reflection)

    // WebJars & Swagger UI
    implementation(libs.jquery)
    implementation(libs.ktor.swagger.ui)
    implementation(libs.swagger.models)

    // Database
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.json)
    implementation(libs.exposed.kotlin.datetime)
    implementation(libs.postgresql)
    implementation(libs.flyway.core)
    implementation(libs.flyway.database.postgresql)
    implementation(libs.h2)

    // Koin
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)

    // Logging
    implementation(libs.logback.classic)
    implementation(libs.slf4j.api)
    implementation(libs.ktor.call.logging)

    // Side libraries
    implementation(libs.kverify.core)
    implementation(libs.kverify.rule.set)

    // Testing
    testImplementation(libs.ktor.test.host)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.property)
    testImplementation(libs.mockk)
}

// Verification
tasks.register("verifyMigrations") {
    doLast {
        val migrationDir = file("src/main/resources/db/migration")
        val migrationFiles = migrationDir.listFiles()?.filter { it.name.endsWith(".sql") }

        migrationFiles?.forEach { file ->
            if (!file.name.matches(Regex("^V[0-9]+(?:_[0-9]+)?__[\\w]+\\.sql$"))) {
                throw GradleException(
                    "Invalid migration filename: ${file.name}\n" +
                        "Migration files must follow the pattern: V<Version>__<Description>.sql\n" +
                        "Example: V1__Initial.sql or V1_1__Add_users_table.sql",
                )
            }
        }
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

// // Build
tasks.withType<ShadowJar> {
    mergeServiceFiles()
    manifest {
        attributes(mapOf("Main-Class" to application.mainClass))
    }
    exclude("application.conf")
    from("src/main/resources") {
        include("db/migration/**")
    }

    dependsOn("test")
}
