# Local Setup Tutorial

URL: https://ci.apache.org/projects/flink/flink-docs-release-1.7/tutorials/local_setup.html

## Start a Local Flink Cluster

```bash
$ ./bin/start-cluster.sh
```

You can also verify that the system is running by checking the log files in the `logs` directory:

```bash
$ tail log/flink-*-standalonesession-*.log
```

## Stop Flink

To stop Flink when you’re done type:

```bash
$ ./bin/stop-cluster.sh
```





