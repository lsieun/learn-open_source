# Monitoring the build


The most popular way of starting a Maven build is by using the `mvn clean install` command. This will build all the Maven modules under your project and install the artifacts to your local repository. 

For a simple project, the entire build process will take less than a minute. However, for a large project, to create an online build with a clean repository could even take more than 3 hours: this is not an exaggeration. If you look at the WSO2 Carbon complete code base, the complete build process takes more than four hours to run with all the test cases. During a long-running build process, it is extremely important that we monitor the build properly.

The `JVisualVM` tool that comes with the `JDK` distribution can be used to monitor a running Maven build. 

JVisualVM is a Java virtual machine monitoring, troubleshooting, and profiling tool. To learn more about it, refer http://docs.oracle.com/javase/6/docs/technotes/tools/share/jvisualvm.html.

First, we need to start the Maven build and then start `JVisualVM` using the following command:

```bash
$ jvisualvm
```

This command will start the `JVisualVM` tool. Once the tool gets started, select `org.codehaus.plexus.classworlds.launcher.Launcher` from the Applications tab to monitor the running Maven build. You can gather many important statistics using JVisualVM, and based on that you can optimize your system resources for an optimal Maven build.





