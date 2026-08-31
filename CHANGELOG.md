# Changelog

## 2.0.0 - 2026-08-30

- Updated Kotlin from 1.9.x to 2.4.10
- Updated Spring Boot from 3.2.3 to 4.1.1
- Updated Gradle from 8.4 to 9.7.1
- Updated Dokka from 1.9.20 to 2.2.0
- Updated Detekt from 1.23.6 to 2.0.0-alpha.6
- Updated SonarQube plugin from 4.4.1.3373 to 7.4.0.8496
- Updated NMCP from 0.0.7 to 1.6.1
- Updated Foojay Toolchain Resolver from 0.5.0 to 1.0.0
- Updated JUnit from 5.10.2 to 5.14.4
- Updated MockK from 1.13.10 to 1.14.11
- Updated Jakarta Persistence API from 3.1.0 to 3.2.0
- Updated ANTLR from 4.13.1 to 4.13.2
- Updated Spring Dependency Management from 1.1.4 to 1.1.7
- Added standalone Hibernate example application

### Breaking changes

- New requirements:
  - Java 17+
  - Kotlin 2.x (K2 compiler) if your project is using Kotlin
  - Spring Boot 4.x if your project is using Spring Boot
- Contract/API changes:
  - `DynamicQueryRepository<T>` now has a `T : Any` bound (nullable type arguments are no longer allowed)
  - `DynamicQuerySpecificationAdapter<T>` now has a `T : Any` bound
  - `ScopeDownSupplier<T>` now has a `T : Any` bound
  - Removed `DynamicQueryRepository.delete(DynamicQuerySpecification?)` and 
   `delete(DynamicQuerySpecification?, ScopeDownSupplier<T>)` (Spring Data API changes no longer allows a Dynamic Query
    to be used in a delete and/or update operation)

## 1.1.0 - 2024-02-25

- Improved Maven publication metadata
- Minor fixes

## 1.0.0 - 2024-02-25

- Initial release
- DSL grammar and parser (ANTLR-based)
- JPA Specification APIs
- Spring Boot autoconfiguration, Data JPA, and Web modules
- Detekt and SonarQube integration
- Maven Central publication support
- GitHub Actions CI/CD
- Attribute references and transformation operations in DSL
- Path conversion APIs
- Enum type value parsing
- Spring Boot example application
