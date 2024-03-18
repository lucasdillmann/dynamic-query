plugins {
    `maven-publish`
    `java-library`
    jacoco
    signing

    kotlin("jvm") version "1.9.10"
    kotlin("plugin.spring") version "1.9.10"
    kotlin("plugin.jpa") version "1.9.10"

    id("io.gitlab.arturbosch.detekt") version "1.23.3"
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.sonarqube") version "4.4.1.3373"
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")
    apply(plugin = "java-library")
    apply(plugin = "signing")
    apply(plugin = "jacoco")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.sonarqube")

    group = "br.com.dillmann.dynamicquery"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }

    dependencies {
        // Kotlin
        testImplementation("org.jetbrains.kotlin:kotlin-test")

        // MockK
        val mockkVersion: String by project
        testImplementation("io.mockk:mockk:$mockkVersion")

        // JUnit
        val junitVersion: String by project
        testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    tasks {
        test {
            useJUnitPlatform()
        }

        findByName("bootJar")?.run {
            enabled = false
        }

        jacocoTestReport {
            reports {
                xml.required = true
                html.required = true
            }
        }

    }

    kotlin {
        jvmToolchain(17)
    }

    java {
        withJavadocJar()
        withSourcesJar()
    }

    sonar {
        properties {
            property("sonar.projectKey", "lucasdillmann_dynamic-query")
            property("sonar.organization", "lucasdillmann")
            property("sonar.host.url", "https://sonarcloud.io")
        }
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()
                from(components["kotlin"])

                versionMapping {
                    usage("java-api") {
                        fromResolutionOf("runtimeClasspath")
                    }
                    usage("java-runtime") {
                        fromResolutionResult()
                    }
                }

                pom {
                    name = "Dynamic Query"
                    description = "Simple and dynamic queries filters for JPA-based applications"
                    url = "https://github.com/lucasdillmann/dynamic-query"

                    licenses {
                        license {
                            name = "MIT License"
                            url = "https://github.com/lucasdillmann/dynamic-query/blob/main/LICENSE.md"
                        }
                    }

                    developers {
                        developer {
                            id = "lucasdillmann"
                            name = "Lucas Dillmann"
                            url = "https://www.dillmann.com.br"
                        }
                    }

                    scm {
                        url = "https://github.com/lucasdillmann/dynamic-query"
                        tag = project.version.toString()
                    }
                }
            }
        }
    }

    signing {
        sign(publishing.publications["maven"])
    }
}


