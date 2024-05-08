dependencies {
    // JPA
    val jakartaPersistenceApiVersion: String by project
    api("jakarta.persistence:jakarta.persistence-api:$jakartaPersistenceApiVersion")
}

publishing {
    publications {
        getByName<MavenPublication>("maven") {
            pom {
                name = "Dynamic Query - JPA Specification APIs"
                description = "Dynamic Query's integration with the JPA APIs using the Specification pattern"
            }
        }
    }
}
