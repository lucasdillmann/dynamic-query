plugins {
    `maven-publish`
    `java-library`
    signing
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.spring") version "1.9.10"
    kotlin("plugin.jpa") version "1.9.10"
    id("io.gitlab.arturbosch.detekt") version "1.23.3"
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")
    apply(plugin = "java-library")
    apply(plugin = "signing")
    apply(plugin = "io.gitlab.arturbosch.detekt")

    group = "br.com.dillmann.dynamicquery"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation("org.jetbrains.kotlin:kotlin-test")
        testImplementation("io.mockk:mockk:1.13.10")
        testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    }

    tasks.test {
        useJUnitPlatform()
    }

    kotlin {
        jvmToolchain(17)
    }

    java {
        withJavadocJar()
        withSourcesJar()
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


