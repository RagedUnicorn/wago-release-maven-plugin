# Wago Release Maven Plugin 2.1.2

## Improvements

* Added an integration test that executes the `wago-release` goal end-to-end with the
  `maven-invoker-plugin`. Previously the build only covered compilation, unit tests and static
  analysis, so runtime classpath failures such as a missing dependency in the plugin's class realm
  were invisible to CI.
* Added the `maven-dependency-plugin` `analyze-only` check to the `verify` phase to catch used but
  undeclared and declared but unused dependencies.
* Replaced the unused `maven-compat` dependency with `maven-settings`, which is the artifact the
  plugin actually uses, and replaced the unused `javax.ws.rs:jsr311-api` dependency with the
  `httpcore` artifact the HTTP client relies on.

## Dependencies

* Updated `checkstyle` to 13.8.0
* Updated `pmd-velocity` and `pmd-xml` to 7.26.0
* Updated `central-publishing-maven-plugin` to 0.11.0
* Updated GitHub Actions
