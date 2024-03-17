plugins {
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

dependencies {
    // Dynamic Query
    implementation(project(":dynamic-query-core"))

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}
