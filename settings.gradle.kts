plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "dynamicquery"

include("dynamic-query")
include("dynamic-query-grammar")
include("dynamic-query-specification")
include("dynamic-query-spring-boot-autoconfiguration")
include("dynamic-query-spring-boot-web")
include("dynamic-query-spring-boot-data-jpa")
