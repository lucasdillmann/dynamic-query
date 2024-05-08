plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

dependencies {
    // Dynamic Query
    api(project(":dynamic-query-spring-boot-autoconfiguration"))

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

publishing {
    publications {
        getByName<MavenPublication>("maven") {
            pom {
                name = "Dynamic Query - Spring Boot Web"
                description = "Dynamic Query's abstractions for easy and simple integration with the Spring Boot Web APIs"
            }
        }
    }
}
