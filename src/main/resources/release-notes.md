# Wago Release Maven Plugin 2.2.0

## Features

* Added support for **World of Warcraft: Forever** patch versions via the new
  `supportedForeverPatch` parameter (property `supportForeverPatch`). At least one of
  `supportedRetailPatch`, `supportedMopPatch`, `supportedCataPatch`, `supportedWotlkcPatch`,
  `supportedBccPatch`, `supportedForeverPatch` or `supportedClassicPatch` must be set. The
  serialized metadata gains the `supported_forever_patch` field.

## Improvements

* Added `timeout-minutes` to all GitHub Actions workflow jobs so a hung step is cancelled after
  15 minutes instead of running to the 6-hour runner default.
* Replaced the README banner with a generated SVG banner.

## Dependencies

* Updated `checkstyle` to 14.3.0
* Updated `pmd-velocity` and `pmd-xml` to 7.28.0
* Updated `maven-plugin-api`, `maven-core` and `maven-settings` to 3.10.0
* Updated `maven-plugin-annotations` and `maven-plugin-plugin` to 3.16.0
* Updated `maven-compiler-plugin` to 3.16.0
* Updated `maven-deploy-plugin` to 3.2.0
* Updated `central-publishing-maven-plugin` to 0.12.0
* Updated `github-release-maven-plugin` to 1.0.9
* Updated `slf4j-api` to 2.0.20
* Updated `mockito-core` to 5.24.0
* Updated GitHub Actions
