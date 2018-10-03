# maven clean plugin

## mvn command

To find more details about **the Maven clean plugin**, type the following command. It describes all the goals defined inside the clean plugin:

```bash
$ mvn help:describe -Dplugin=clean
```

Everything in Maven is a plugin. Even the command we executed previously to get goal details of **the clean plugin** executes another plugin: **the help plugin**. The following command will describe **the help plugin** itself:

```bash
$ mvn help:describe -Dplugin=help
```

describe is a goal defined inside the help plugin.



