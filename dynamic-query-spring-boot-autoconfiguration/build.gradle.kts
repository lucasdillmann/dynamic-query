plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

dependencies {
    // Dynamic Query
    api(project(":dynamic-query"))

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks {
    bootJar {
        enabled = false
        mainClass = "" // even though the task is disabled, Spring plugin still requires a value to be set
    }
}
