plugins {
    antlr
}

dependencies {
    // ANTLR
    antlr("org.antlr:antlr4:4.13.1")

    // JPA
    compileOnly("jakarta.persistence:jakarta.persistence-api:2.2.3")
    testImplementation("jakarta.persistence:jakarta.persistence-api:2.2.3")
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
