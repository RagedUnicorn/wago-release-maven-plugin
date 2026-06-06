# Wago Release Maven Plugin 2.1.0

## Features

* Added support for **Mists of Pandaria** and **Cataclysm** patch versions via the new
  `supportMopPatch` and `supportCataPatch` parameters. At least one of `supportedRetailPatch`,
  `supportedMopPatch`, `supportedCataPatch`, `supportedWotlkcPatch`, `supportedBccPatch` or
  `supportedClassicPatch` must now be set. The serialized metadata gains `supported_mop_patch`
  and `supported_cata_patch` fields.

## Build & Tooling

* PMD upgraded to 7.24.0.
* README Maven Central badge link fixed.