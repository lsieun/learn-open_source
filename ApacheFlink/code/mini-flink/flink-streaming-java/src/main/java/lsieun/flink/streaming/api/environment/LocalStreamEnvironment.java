package lsieun.flink.streaming.api.environment;

import javax.annotation.Nonnull;

import lsieun.flink.annotation.Public;
import lsieun.flink.configuration.Configuration;

@Public
public class LocalStreamEnvironment extends StreamExecutionEnvironment {
    private final Configuration configuration;

    public LocalStreamEnvironment() {
        this(new Configuration());
    }

    public LocalStreamEnvironment(@Nonnull Configuration configuration) {
        this.configuration = configuration;
        setParallelism(1);
    }

    public StreamExecutionEnvironment setParallelism(int parallelism) {
        config.setParallelism(parallelism);
        return this;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
