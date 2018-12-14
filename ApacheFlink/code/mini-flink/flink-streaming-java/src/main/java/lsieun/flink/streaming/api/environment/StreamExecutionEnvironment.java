package lsieun.flink.streaming.api.environment;

import lsieun.flink.annotation.Public;
import lsieun.flink.api.common.ExecutionConfig;
import lsieun.flink.api.common.JobExecutionResult;
import lsieun.flink.configuration.Configuration;
import lsieun.flink.core.fs.Path;
import lsieun.flink.util.Preconditions;

@Public
public abstract class StreamExecutionEnvironment {

    /** The default name to use for a streaming job if no other name has been specified. */
    public static final String DEFAULT_JOB_NAME = "Flink Streaming Job";

    /** The execution configuration for this environment. */
    protected final ExecutionConfig config = new ExecutionConfig();

    /** The default parallelism used when creating a local environment. */
    private static int defaultLocalParallelism = Runtime.getRuntime().availableProcessors();

    public static StreamExecutionEnvironment getExecutionEnvironment() {
        return createLocalEnvironment();
    }

    public static LocalStreamEnvironment createLocalEnvironment() {
        return createLocalEnvironment(defaultLocalParallelism);
    }

    public static LocalStreamEnvironment createLocalEnvironment(int parallelism) {
        return createLocalEnvironment(parallelism, new Configuration());
    }

    public static LocalStreamEnvironment createLocalEnvironment(int parallelism, Configuration configuration) {
        final LocalStreamEnvironment currentEnvironment;

        currentEnvironment = new LocalStreamEnvironment(configuration);
        currentEnvironment.setParallelism(parallelism);

        return currentEnvironment;
    }

    // FIXME: 003 DataStreamSource
    public DataStreamSource<String> readTextFile(String filePath) {
        return readTextFile(filePath, "UTF-8");
    }

    // FIXME: 004 TextInputFormat
    // FIXME: 005 Path
    public DataStreamSource<String> readTextFile(String filePath, String charsetName) {
        Preconditions.checkNotNull(filePath, "The file path must not be null.");
        Preconditions.checkNotNull(filePath.isEmpty(), "The file path must not be empty.");

        TextInputFormat format = new TextInputFormat(new Path(filePath));
        format.setFilesFilter(FilePathFilter.createDefaultFilter());
        TypeInformation<String> typeInfo = BasicTypeInfo.STRING_TYPE_INFO;
        format.setCharsetName(charsetName);

        return readFile(format, filePath, FileProcessingMode.PROCESS_ONCE, -1, typeInfo);
    }

    // FIXME: 001 JobExecutionResult
    // FIXME: 002 implementation
    public abstract JobExecutionResult execute(String jobName) throws Exception;
}
