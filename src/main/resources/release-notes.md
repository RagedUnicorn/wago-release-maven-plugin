# Wago Release Maven Plugin 2.0.0

## Breaking Changes

* **Minimum Java version is now 21.** Previously Java 8. The plugin compiles with `<release>21</release>` and consumers must run Maven on JDK 21 or newer.

## Internal

* Replaced the project's hand-rolled `DefaultLog` / `WagoReleaseLogger` with standard SLF4J (`org.slf4j.Logger` via `LoggerFactory`). Log output now flows through Maven's standard logging system at runtime.
* `ReleaseService.createReleaseOperation` now uses try-with-resources for `CloseableHttpClient` and `CloseableHttpResponse`, eliminating a potential resource leak on `IOException`.

## Build & Tooling

* PMD upgraded from 6.6 to 7.17; `maven-pmd-plugin` upgraded from 3.10 to 3.28 (PMD 7 default).
* Checkstyle upgraded from 8.29 to 13.4.2.
* `maven-code-quality` ruleset dependency bumped from 1.0.6 to 2.1.0 (PMD 7 / Checkstyle 13 compatible rulesets).
* `maven-plugin-api`, `maven-core`, `maven-compat` aligned at 3.9.15 and moved to `provided` scope, clearing the "dependencies in wrong scope" build warning.
* CI runs on Temurin Java 21.
* Release publishing migrated from the sunset Sonatype OSSRH endpoint to the new Sonatype Central Portal (`central.sonatype.com`). The publishing workflow is now `Maven Central Package Release`.
