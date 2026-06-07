# Wago Release Maven Plugin 2.1.1

## Bugfixes

* Fixed a `NoClassDefFoundError: com/google/common/collect/Lists` at execution time. The plugin
  referenced Guava but never declared it as a dependency, so it was only resolved transitively
  through the `provided`-scope Maven core at compile time and was absent from the plugin's runtime
  class realm. The single Guava usage was replaced with a JDK equivalent, removing the dependency
  entirely. Affected releases: 2.1.0.