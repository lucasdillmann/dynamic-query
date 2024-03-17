plugins {
    antlr
}

dependencies {
    // ANTLR
    antlr("org.antlr:antlr4:4.13.1")

    // JPA
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
}

tasks {
    compileKotlin {
        dependsOn(generateGrammarSource)
    }

    compileTestKotlin {
        dependsOn(generateTestGrammarSource)
    }

    sourcesJar {
        dependsOn(generateGrammarSource)
    }
}
