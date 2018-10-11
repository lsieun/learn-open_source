# Multiple repositories

Each Maven project has its own effective POM file. **The effective POM file** is the aggregated POM file from the application POM, all parent POM files, and the super POM. Finally, what matters to Maven is the effective POM, not the individual ones. Each individual POM file can have its own repositories defined under the `repositories` section, but in the effective POM file, there will be one single `repositories` section, which aggregates all the repositories defined in each POM file.

When you have multiple repositories defined in the POM, the order in which they are defined matters. Whenever Maven detects that a required artifact is missing in the `local` repository, it will try to download from the very first eligible repository defined in the effective POM file. When Maven generates the effective POM, the top repositories will be taken from the application POM, then from the parent POM files and finally from the super POM. Maven will move down the repositories in the order they are defined in the effective POM if it cannot find an artifact in a given repository.



