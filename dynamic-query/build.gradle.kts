dependencies {
    api(project(":dynamic-query-grammar"))
    api(project(":dynamic-query-specification"))
}

publishing {
    publications {
        getByName<MavenPublication>("maven") {
            pom {
                name = "Dynamic Query"
                description = "Dynamic Query's standalone APIs for use with any JPA compatible ORM"
            }
        }
    }
}
