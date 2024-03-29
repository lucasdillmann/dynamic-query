dependencies {
    // JPA
    val jakartaPersistenceApiVersion: String by project
    api("jakarta.persistence:jakarta.persistence-api:$jakartaPersistenceApiVersion")
}
