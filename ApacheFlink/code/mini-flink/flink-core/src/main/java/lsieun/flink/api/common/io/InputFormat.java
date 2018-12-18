package lsieun.flink.api.common.io;

import java.io.IOException;
import java.io.Serializable;

import lsieun.flink.annotation.Public;
import lsieun.flink.api.common.io.statistics.BaseStatistics;
import lsieun.flink.configuration.Configuration;
import lsieun.flink.core.io.InputSplit;
import lsieun.flink.core.io.InputSplitAssigner;
import lsieun.flink.core.io.InputSplitSource;

@Public
public interface InputFormat<OT, T extends InputSplit> extends InputSplitSource<T>, Serializable {

    /**
     * Configures this input format. Since input formats are instantiated generically and hence parameterless,
     * this method is the place where the input formats set their basic fields based on configuration values.
     * <p>
     * This method is always called first on a newly instantiated input format.
     *
     * @param parameters The configuration with all parameters (note: not the Flink config but the TaskConfig).
     */
    void configure(Configuration parameters);

    /**
     * Gets the basic statistics from the input described by this format. If the input format does not know how
     * to create those statistics, it may return null.
     * This method optionally gets a cached version of the statistics. The input format may examine them and decide
     * whether it directly returns them without spending effort to re-gather the statistics.
     * <p>
     * When this method is called, the input format it guaranteed to be configured.
     *
     * @param cachedStatistics The statistics that were cached. May be null.
     * @return The base statistics for the input, or null, if not available.
     */
    BaseStatistics getStatistics(BaseStatistics cachedStatistics) throws IOException;

    // --------------------------------------------------------------------------------------------

    /**
     * Creates the different splits of the input that can be processed in parallel.
     * <p>
     * When this method is called, the input format it guaranteed to be configured.
     *
     * @param minNumSplits The minimum desired number of splits. If fewer are created, some parallel
     *                     instances may remain idle.
     * @return The splits of this input that can be processed in parallel.
     *
     * @throws IOException Thrown, when the creation of the splits was erroneous.
     */
    @Override
    T[] createInputSplits(int minNumSplits) throws IOException;

    /**
     * Gets the type of the input splits that are processed by this input format.
     *
     * @return The type of the input splits.
     */
    @Override
    InputSplitAssigner getInputSplitAssigner(T[] inputSplits);

    // --------------------------------------------------------------------------------------------

    /**
     * Opens a parallel instance of the input format to work on a split.
     * <p>
     * When this method is called, the input format it guaranteed to be configured.
     *
     * @param split The split to be opened.
     * @throws IOException Thrown, if the spit could not be opened due to an I/O problem.
     */
    void open(T split) throws IOException;

    /**
     * Method used to check if the end of the input is reached.
     * <p>
     * When this method is called, the input format it guaranteed to be opened.
     *
     * @return True if the end is reached, otherwise false.
     * @throws IOException Thrown, if an I/O error occurred.
     */
    boolean reachedEnd() throws IOException;

    /**
     * Reads the next record from the input.
     * <p>
     * When this method is called, the input format it guaranteed to be opened.
     *
     * @param reuse Object that may be reused.
     * @return Read record.
     *
     * @throws IOException Thrown, if an I/O error occurred.
     */
    OT nextRecord(OT reuse) throws IOException;

    /**
     * Method that marks the end of the life-cycle of an input split. Should be used to close channels and streams
     * and release resources. After this method returns without an error, the input is assumed to be correctly read.
     * <p>
     * When this method is called, the input format it guaranteed to be opened.
     *
     * @throws IOException Thrown, if the input could not be closed properly.
     */
    void close() throws IOException;
}
