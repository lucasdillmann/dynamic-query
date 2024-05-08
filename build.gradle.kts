import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    val kotlinVersion = "1.9.23"
    val dokkaVersion = "1.9.20"
    val sonarqubeVersion = "4.4.1.3373"
    val detektVersion = "1.23.6"
    val nmcpVersion = "0.0.7"
    val springBootVersion = "3.2.3"
    val springDependencyManagementVersion = "1.1.4"

    `maven-publish`
    `java-library`
    jacoco
    signing

    kotlin("jvm") version "1.9.10"
    kotlin("plugin.spring") version kotlinVersion apply false
    kotlin("plugin.jpa") version kotlinVersion apply false

    id("org.sonarqube") version sonarqubeVersion
    id("io.gitlab.arturbosch.detekt") version detektVersion
    id("com.gradleup.nmcp") version nmcpVersion
    id("org.jetbrains.dokka") version dokkaVersion apply false
    id("org.springframework.boot") version springBootVersion apply false
    id("io.spring.dependency-management") version springDependencyManagementVersion apply false
}

allprojects {
    group = "br.com.dillmann.dynamicquery"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }

    sonar {
        properties {
            property("sonar.projectKey", "lucasdillmann_dynamic-query")
            property("sonar.organization", "lucasdillmann")
            property("sonar.host.url", "https://sonarcloud.io")
        }
    }
}

gradle.rootProject {
    nmcp {
        publishAggregation {
            subprojects.forEach {
                project(":${it.name}")
            }

            username = providers.environmentVariable("MAVEN_CENTRAL_USERNAME")
            password = providers.environmentVariable("MAVEN_CENTRAL_PASSWORD")
            publicationType = "USER_MANAGED"
        }
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")
    apply(plugin = "java-library")
    apply(plugin = "signing")
    apply(plugin = "jacoco")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.sonarqube")
    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "com.gradleup.nmcp")

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

        create<Jar>("dokkaJavadocJar") {
            val dokkaJavadoc = getByName("dokkaJavadoc") as DokkaTask
            dependsOn(dokkaJavadoc)

            archiveClassifier = "javadoc"
            from(dokkaJavadoc.outputDirectory)
        }

        afterEvaluate {
            withType<AbstractPublishToMaven>().configureEach {
                dependsOn(kotlinSourcesJar)
                dependsOn(getByName("sourcesJar"))
                dependsOn(getByName("dokkaJavadocJar"))
            }

            withType<Sign>().configureEach {
                dependsOn(getByName("sourcesJar"))
            }
        }
    }

    kotlin {
        jvmToolchain(17)
        compilerOptions {
            freeCompilerArgs.add("-Xjvm-default=all")
        }
    }

    java {
        withSourcesJar()
    }

    nmcp {
        publishAllPublications {}
    }

    signing {
        val signingKey = providers.environmentVariable("MAVEN_CENTRAL_SIGNING_KEY")
        val signingSecret = providers.environmentVariable("MAVEN_CENTRAL_SIGNING_SECRET")
        if (signingKey.isPresent && signingSecret.isPresent) {
            useInMemoryPgpKeys(signingKey.get(), signingSecret.get())

            afterEvaluate {
                sign(publishing.publications)
            }
        }
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()

                from(components["kotlin"])
                artifact(tasks.kotlinSourcesJar)
                artifact(tasks.findByName("dokkaJavadocJar"))

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
}


