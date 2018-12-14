package lsieun.flink.api.common.io;

import static lsieun.flink.util.Preconditions.checkNotNull;

import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lsieun.flink.annotation.Public;
import lsieun.flink.api.common.io.statistics.BaseStatistics;
import lsieun.flink.configuration.ConfigConstants;
import lsieun.flink.configuration.Configuration;
import lsieun.flink.configuration.GlobalConfiguration;
import lsieun.flink.core.fs.FSDataInputStream;
import lsieun.flink.core.fs.FileInputSplit;
import lsieun.flink.core.fs.Path;
import lsieun.flink.util.Preconditions;

/**
 * The base class for {@link RichInputFormat}s that read from files. For specific input types the
 * {@link #nextRecord(Object)} and {@link #reachedEnd()} methods need to be implemented.
 * Additionally, one may override {@link #open(FileInputSplit)} and {@link #close()} to
 * change the life cycle behavior.
 *
 * <p>After the {@link #open(FileInputSplit)} method completed, the file input data is available
 * from the {@link #stream} field.</p>
 */
@Public
public abstract class FileInputFormat<OT> extends RichInputFormat<OT, FileInputSplit> {

    // -------------------------------------- Constants -------------------------------------------

    private static final Logger LOG = LoggerFactory.getLogger(FileInputFormat.class);

    private static final long serialVersionUID = 1L;


    /**
     * The fraction that the last split may be larger than the others.
     * <p>discrepancy 差异；不一致</p>
     */
    private static final float MAX_SPLIT_SIZE_DISCREPANCY = 1.1f;

    /**
     * The timeout (in milliseconds) to wait for a filesystem stream to respond.
     */
    private static long DEFAULT_OPENING_TIMEOUT;

    /**
     * The splitLength is set to -1L for reading the whole split.
     */
    protected static final long READ_WHOLE_SPLIT_FLAG = -1L;

    static {
        initDefaultsFromConfiguration(GlobalConfiguration.loadConfiguration());
    }

    /**
     * Initialize defaults for input format. Needs to be a static method because it is configured for local
     * cluster execution, see LocalFlinkMiniCluster.
     * @param configuration The configuration to load defaults from
     */
    private static void initDefaultsFromConfiguration(Configuration configuration) {
        final long to = configuration.getLong(ConfigConstants.FS_STREAM_OPENING_TIMEOUT_KEY,
                ConfigConstants.DEFAULT_FS_STREAM_OPENING_TIMEOUT);
        if (to < 0) {
            LOG.error("Invalid timeout value for filesystem stream opening: " + to + ". Using default value of " +
                    ConfigConstants.DEFAULT_FS_STREAM_OPENING_TIMEOUT);
            DEFAULT_OPENING_TIMEOUT = ConfigConstants.DEFAULT_FS_STREAM_OPENING_TIMEOUT;
        } else if (to == 0) {
            DEFAULT_OPENING_TIMEOUT = 300000; // 5 minutes
        } else {
            DEFAULT_OPENING_TIMEOUT = to;
        }
    }

    /**
     * Returns the extension of a file name (!= a path).
     * @return the extension of the file name or {@code null} if there is no extension.
     */
    protected static String extractFileExtension(String fileName) {
        checkNotNull(fileName);
        int lastPeriodIndex = fileName.lastIndexOf('.');
        if (lastPeriodIndex < 0){
            return null;
        } else {
            return fileName.substring(lastPeriodIndex + 1);
        }
    }

    // --------------------------------------------------------------------------------------------
    //  Variables for internal operation.
    //  They are all transient, because we do not want them so be serialized
    // --------------------------------------------------------------------------------------------

    /**
     * The input stream reading from the input file.
     */
    protected transient FSDataInputStream stream;

    /**
     * The start of the split that this parallel instance must consume.
     */
    protected transient long splitStart;

    /**
     * The length of the split that this parallel instance must consume.
     */
    protected transient long splitLength;

    /**
     * The current split that this parallel instance must consume.
     */
    protected transient FileInputSplit currentSplit;


    // --------------------------------------------------------------------------------------------
    //  The configuration parameters. Configured on the instance and serialized to be shipped.
    // --------------------------------------------------------------------------------------------


    /**
     * The path to the file that contains the input.
     *
     * @deprecated Please override {@link FileInputFormat#supportsMultiPaths()} and
     *             use {@link FileInputFormat#getFilePaths()} and {@link FileInputFormat#setFilePaths(Path...)}.
     */
    @Deprecated
    protected Path filePath;

    /**
     * The list of paths to files and directories that contain the input.
     */
    private Path[] filePaths;

    /**
     * The minimal split size, set by the configure() method.
     */
    protected long minSplitSize = 0;

    /**
     * The desired number of splits, as set by the configure() method.
     */
    protected int numSplits = -1;

    /**
     * Stream opening timeout.
     */
    protected long openTimeout = DEFAULT_OPENING_TIMEOUT;

    /**
     * Some file input formats are not splittable on a block level (avro, deflate)
     * Therefore, the FileInputFormat can only read whole files.
     */
    protected boolean unsplittable = false;

    /**
     * The flag to specify whether recursive traversal of the input directory
     * structure is enabled.
     */
    protected boolean enumerateNestedFiles = false;

    /**
     * Files filter for determining what files/directories should be included.
     */
    private FilePathFilter filesFilter = new GlobFilePathFilter();


	// --------------------------------------------------------------------------------------------
	//  Constructors
	// --------------------------------------------------------------------------------------------

	public FileInputFormat() {}

	protected FileInputFormat(Path filePath) {
		if (filePath != null) {
			setFilePath(filePath);
		}
	}

    // --------------------------------------------------------------------------------------------
    //  Getters/setters for the configurable parameters
    // --------------------------------------------------------------------------------------------

    /**
     *
     * @return The path of the file to read.
     *
     * @deprecated Please use getFilePaths() instead.
     */
    @Deprecated
    public Path getFilePath() {

        if (supportsMultiPaths()) {
            if (this.filePaths == null || this.filePaths.length == 0) {
                return null;
            } else if (this.filePaths.length == 1) {
                return this.filePaths[0];
            } else {
                throw new UnsupportedOperationException(
                        "FileInputFormat is configured with multiple paths. Use getFilePaths() instead.");
            }
        } else {
            return filePath;
        }
    }

    /**
     * Returns the paths of all files to be read by the FileInputFormat.
     *
     * @return The list of all paths to read.
     */
    public Path[] getFilePaths() {

        if (supportsMultiPaths()) {
            if (this.filePaths == null) {
                return new Path[0];
            }
            return this.filePaths;
        } else {
            if (this.filePath == null) {
                return new Path[0];
            }
            return new Path[] {filePath};
        }
    }

    public void setFilePath(String filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException("File path cannot be null.");
        }

        // TODO The job-submission web interface passes empty args (and thus empty
        // paths) to compute the preview graph. The following is a workaround for
        // this situation and we should fix this.

        // comment (Stephan Ewen) this should be no longer relevant with the current Java/Scala APIs.
        if (filePath.isEmpty()) {
            setFilePath(new Path());
            return;
        }

        try {
            this.setFilePath(new Path(filePath));
        } catch (RuntimeException rex) {
            throw new RuntimeException("Could not create a valid URI from the given file path name: " + rex.getMessage());
        }
    }

    /**
     * Sets a single path of a file to be read.
     *
     * @param filePath The path of the file to read.
     */
    public void setFilePath(Path filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException("File path must not be null.");
        }

        setFilePaths(filePath);
    }

    /**
     * Sets multiple paths of files to be read.
     *
     * @param filePaths The paths of the files to read.
     */
    public void setFilePaths(String... filePaths) {
        Path[] paths = new Path[filePaths.length];
        for (int i = 0; i < paths.length; i++) {
            paths[i] = new Path(filePaths[i]);
        }
        setFilePaths(paths);
    }

    /**
     * Sets multiple paths of files to be read.
     *
     * @param filePaths The paths of the files to read.
     */
    public void setFilePaths(Path... filePaths) {
        if (!supportsMultiPaths() && filePaths.length > 1) {
            throw new UnsupportedOperationException(
                    "Multiple paths are not supported by this FileInputFormat.");
        }
        if (filePaths.length < 1) {
            throw new IllegalArgumentException("At least one file path must be specified.");
        }
        if (filePaths.length == 1) {
            // set for backwards compatibility
            this.filePath = filePaths[0];
        } else {
            // clear file path in case it had been set before
            this.filePath = null;
        }

        this.filePaths = filePaths;
    }

    public long getMinSplitSize() {
        return minSplitSize;
    }

    public void setMinSplitSize(long minSplitSize) {
        if (minSplitSize < 0) {
            throw new IllegalArgumentException("The minimum split size cannot be negative.");
        }

        this.minSplitSize = minSplitSize;
    }

    public int getNumSplits() {
        return numSplits;
    }

    public void setNumSplits(int numSplits) {
        if (numSplits < -1 || numSplits == 0) {
            throw new IllegalArgumentException("The desired number of splits must be positive or -1 (= don't care).");
        }

        this.numSplits = numSplits;
    }

    public long getOpenTimeout() {
        return openTimeout;
    }

    public void setOpenTimeout(long openTimeout) {
        if (openTimeout < 0) {
            throw new IllegalArgumentException("The timeout for opening the input splits must be positive or zero (= infinite).");
        }
        this.openTimeout = openTimeout;
    }

    public void setNestedFileEnumeration(boolean enable) {
        this.enumerateNestedFiles = enable;
    }

    public boolean getNestedFileEnumeration() {
        return this.enumerateNestedFiles;
    }

    // --------------------------------------------------------------------------------------------
    // Getting information about the split that is currently open
    // --------------------------------------------------------------------------------------------

    /**
     * Gets the start of the current split.
     *
     * @return The start of the split.
     */
    public long getSplitStart() {
        return splitStart;
    }

    /**
     * Gets the length or remaining length of the current split.
     *
     * @return The length or remaining length of the current split.
     */
    public long getSplitLength() {
        return splitLength;
    }

    public void setFilesFilter(FilePathFilter filesFilter) {
        this.filesFilter = Preconditions.checkNotNull(filesFilter, "Files filter should not be null");
    }

    // --------------------------------------------------------------------------------------------
    //  Pre-flight: Configuration, Splits, Sampling
    // --------------------------------------------------------------------------------------------

    /**
     * Configures the file input format by reading the file path from the configuration.
     *
     * @see lsieun.flink.api.common.io.InputFormat#configure(lsieun.flink.configuration.Configuration)
     */
    @Override
    public void configure(Configuration parameters) {

        if (getFilePaths().length == 0) {
            // file path was not specified yet. Try to set it from the parameters.
            String filePath = parameters.getString(FILE_PARAMETER_KEY, null);
            if (filePath == null) {
                throw new IllegalArgumentException("File path was not specified in input format or configuration.");
            } else {
                setFilePath(filePath);
            }
        }

        if (!this.enumerateNestedFiles) {
            this.enumerateNestedFiles = parameters.getBoolean(ENUMERATE_NESTED_FILES_FLAG, false);
        }
    }

    // --------------------------------------------------------------------------------------------

    /**
     * Opens an input stream to the file defined in the input format.
     * The stream is positioned at the beginning of the given split.
     * <p>
     * The stream is actually opened in an asynchronous thread to make sure any interruptions to the thread
     * working on the input format do not reach the file system.
     */
    @Override
    public void open(FileInputSplit fileSplit) throws IOException {

        this.currentSplit = fileSplit;
        this.splitStart = fileSplit.getStart();
        this.splitLength = fileSplit.getLength();

        if (LOG.isDebugEnabled()) {
            LOG.debug("Opening input split " + fileSplit.getPath() + " [" + this.splitStart + "," + this.splitLength + "]");
        }


        // open the split in an asynchronous thread
        final InputSplitOpenThread isot = new InputSplitOpenThread(fileSplit, this.openTimeout);
        isot.start();

        try {
            this.stream = isot.waitForCompletion();
            this.stream = decorateInputStream(this.stream, fileSplit);
        }
        catch (Throwable t) {
            throw new IOException("Error opening the Input Split " + fileSplit.getPath() +
                    " [" + splitStart + "," + splitLength + "]: " + t.getMessage(), t);
        }

        // get FSDataInputStream
        if (this.splitStart != 0) {
            this.stream.seek(this.splitStart);
        }
    }

    /**
     * Closes the file input stream of the input format.
     */
    @Override
    public void close() throws IOException {
        if (this.stream != null) {
            // close input stream
            this.stream.close();
            stream = null;
        }
    }

    /**
     * Override this method to supports multiple paths.
     * When this method will be removed, all FileInputFormats have to support multiple paths.
     *
     * @return True if the FileInputFormat supports multiple paths, false otherwise.
     *
     * @deprecated Will be removed for Flink 2.0.
     */
    @Deprecated
    public boolean supportsMultiPaths() {
        return false;
    }

    public String toString() {
        return getFilePaths() == null || getFilePaths().length == 0 ?
                "File Input (unknown file)" :
                "File Input (" +  Arrays.toString(this.getFilePaths()) + ')';
    }

    // ============================================================================================

    /**
     * Encapsulation of the basic statistics the optimizer obtains about a file. Contained are the size of the file
     * and the average bytes of a single record. The statistics also have a time-stamp that records the modification
     * time of the file and indicates as such for which time the statistics were valid.
     */
    public static class FileBaseStatistics implements BaseStatistics {

        protected final long fileModTime; // timestamp of the last modification

        protected final long fileSize; // size of the file(s) in bytes

        protected final float avgBytesPerRecord; // the average number of bytes for a record

        /**
         * Creates a new statistics object.
         *
         * @param fileModTime
         *        The timestamp of the latest modification of any of the involved files.
         * @param fileSize
         *        The size of the file, in bytes. <code>-1</code>, if unknown.
         * @param avgBytesPerRecord
         *        The average number of byte in a record, or <code>-1.0f</code>, if unknown.
         */
        public FileBaseStatistics(long fileModTime, long fileSize, float avgBytesPerRecord) {
            this.fileModTime = fileModTime;
            this.fileSize = fileSize;
            this.avgBytesPerRecord = avgBytesPerRecord;
        }

        /**
         * Gets the timestamp of the last modification.
         *
         * @return The timestamp of the last modification.
         */
        public long getLastModificationTime() {
            return fileModTime;
        }

        /**
         * Gets the file size.
         *
         * @return The fileSize.
         * @see org.apache.flink.api.common.io.statistics.BaseStatistics#getTotalInputSize()
         */
        @Override
        public long getTotalInputSize() {
            return this.fileSize;
        }

        /**
         * Gets the estimates number of records in the file, computed as the file size divided by the
         * average record width, rounded up.
         *
         * @return The estimated number of records in the file.
         * @see org.apache.flink.api.common.io.statistics.BaseStatistics#getNumberOfRecords()
         */
        @Override
        public long getNumberOfRecords() {
            return (this.fileSize == SIZE_UNKNOWN || this.avgBytesPerRecord == AVG_RECORD_BYTES_UNKNOWN) ?
                    NUM_RECORDS_UNKNOWN : (long) Math.ceil(this.fileSize / this.avgBytesPerRecord);
        }

        /**
         * Gets the estimated average number of bytes per record.
         *
         * @return The average number of bytes per record.
         * @see org.apache.flink.api.common.io.statistics.BaseStatistics#getAverageRecordWidth()
         */
        @Override
        public float getAverageRecordWidth() {
            return this.avgBytesPerRecord;
        }

        @Override
        public String toString() {
            return "size=" + this.fileSize + ", recWidth=" + this.avgBytesPerRecord + ", modAt=" + this.fileModTime;
        }
    }



    // ============================================================================================
    //  Parameterization via configuration
    // ============================================================================================

    // ------------------------------------- Config Keys ------------------------------------------

    /**
     * The config parameter which defines the input file path.
     */
    private static final String FILE_PARAMETER_KEY = "input.file.path";

    /**
     * The config parameter which defines whether input directories are recursively traversed.
     */
    public static final String ENUMERATE_NESTED_FILES_FLAG = "recursive.file.enumeration";
}
