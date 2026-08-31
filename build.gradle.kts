plugins {
    val kotlinVersion = "2.4.10"
    val dokkaVersion = "2.2.0"
    val sonarqubeVersion = "7.4.0.8496"
    val detektVersion = "2.0.0-alpha.6"
    val nmcpVersion = "1.6.1"
    val springBootVersion = "4.1.1"
    val springDependencyManagementVersion = "1.1.7"

    `maven-publish`
    `java-library`
    jacoco
    signing

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion apply false
    kotlin("plugin.jpa") version kotlinVersion apply false

    id("org.sonarqube") version sonarqubeVersion
    id("dev.detekt") version detektVersion
    id("com.gradleup.nmcp") version nmcpVersion
    id("com.gradleup.nmcp.aggregation") version nmcpVersion
    id("org.jetbrains.dokka") version dokkaVersion apply false
    id("org.springframework.boot") version springBootVersion apply false
    id("io.spring.dependency-management") version springDependencyManagementVersion apply false
}

allprojects {
    group = "br.com.dillmann.dynamicquery"
    version = "2.0.0"

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

nmcpAggregation {
    centralPortal {
        username = providers.environmentVariable("MAVEN_CENTRAL_USERNAME")
        password = providers.environmentVariable("MAVEN_CENTRAL_PASSWORD")
        publishingType = "USER_MANAGED"
    }
}

dependencies {
    subprojects.forEach {
        nmcpAggregation(project(":${it.name}"))
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")
    apply(plugin = "java-library")
    apply(plugin = "signing")
    apply(plugin = "jacoco")
    apply(plugin = "dev.detekt")
    apply(plugin = "org.sonarqube")
    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "com.gradleup.nmcp")

    dependencies {
        // Kotlin
        testImplementation("org.jetbrains.kotlin:kotlin-test")

        // MockK
        val mockkVersion = project.property("mockkVersion")
        testImplementation("io.mockk:mockk:$mockkVersion")

        // JUnit
        val junitVersion = project.property("junitVersion")
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

        register<Jar>("dokkaJavadocJar") {
            dependsOn("dokkaGenerate")

            archiveClassifier = "javadoc"
            from(rootProject.layout.buildDirectory.dir("dokka/javadoc"))
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
            freeCompilerArgs.add("-jvm-default=no-compatibility")
        }
    }

    java {
        withSourcesJar()
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
                    // name and description properties must be defined by the submodules
                    url = "https://github.com/lucasdillmann/dynamic-query"

                    licenses {
                        license {
                            name = "MIT License"
                            url = "https://github.com/lucasdillmann/dynamic-query/blob/${project.version}/LICENSE.md"
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


