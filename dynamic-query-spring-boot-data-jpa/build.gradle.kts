plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

dependencies {
    // Dynamic Query
    api(project(":dynamic-query-spring-boot"))

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Kotlin
    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.9.22")
}

tasks {
    bootJar {
        enabled = false
        mainClass = "" // even though the task is disabled, Spring plugin still requires a value to be set
    }
}
