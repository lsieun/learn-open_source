# Maven: Command to update repository after adding dependency to POM

URL: https://stackoverflow.com/questions/8563960/maven-command-to-update-repository-after-adding-dependency-to-pom


If you want to **only** download dependencies without doing anything else, then it's:

```bash
mvn dependency:resolve
```

Or to download a single dependency:

```bash
mvn dependency:get -Dartifact=groupId:artifactId:version
```

If you need to download from a specific repository, you can specify that with `-DrepoUrl=...`




