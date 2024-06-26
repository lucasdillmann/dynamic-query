plugins {
    antlr
}

dependencies {
    // ANTLR
    val antlrVersion: String by project
    antlr("org.antlr:antlr4:$antlrVersion")
}

tasks {
    listOf(
        compileKotlin,
        kotlinSourcesJar,
        sourcesJar,
        dokkaJavadoc,
    ).forEach {
        it.get().dependsOn(generateGrammarSource)
    }

    compileTestKotlin {
        dependsOn(generateGrammarSource)
        dependsOn(generateTestGrammarSource)
    }

    javadoc {
        options {
            this as StandardJavadocDocletOptions
            addBooleanOption("Xdoclint:none", true)
        }
    }

    jacocoTestReport {
        // Exclude the Dynamic Query's DSL files from the JaCoCo reports since such source is
        // generated by the ANTLR (without the Generated annotation)
        val filteredFiles = classDirectories.files.map {
            fileTree(it).exclude("br/com/dillmann/dynamicquery/core/grammar/dsl/**")
        }

        classDirectories.setFrom(filteredFiles)
    }
}

publishing {
    publications {
        getByName<MavenPublication>("maven") {
            pom {
                name = "Dynamic Query - DSL grammar"
                description = "Dynamic Query's DSL grammar definition and parsers"
            }
        }
    }
}
