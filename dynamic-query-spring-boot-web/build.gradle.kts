plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

dependencies {
    // Dynamic Query
    implementation(project(":dynamic-query-core"))

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web")
}

tasks {
    bootJar {
        enabled = false
        mainClass = "" // even though the task is disabled, Spring plugin still requires a value to be set
    }
}
