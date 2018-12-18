package lsieun.flink.configuration;


import static lsieun.flink.configuration.ConfigOptions.key;

import lsieun.flink.annotation.PublicEvolving;

/**
 * Configuration options for the optimizer.
 */
@PublicEvolving
public class OptimizerOptions {

    /**
     * The maximum number of line samples taken by the compiler for delimited inputs. The samples are used to estimate
     * the number of records. This value can be overridden for a specific input with the input format’s parameters.
     */
    public static final ConfigOption<Integer> DELIMITED_FORMAT_MAX_LINE_SAMPLES =
            key("compiler.delimited-informat.max-line-samples")
                    .defaultValue(10)
                    .withDescription("The maximum number of line samples taken by the compiler for delimited inputs. The samples" +
                            " are used to estimate the number of records. This value can be overridden for a specific input with the" +
                            " input format’s parameters.");

    /**
     * The minimum number of line samples taken by the compiler for delimited inputs. The samples are used to estimate
     * the number of records. This value can be overridden for a specific input with the input format’s parameters.
     */
    public static final ConfigOption<Integer> DELIMITED_FORMAT_MIN_LINE_SAMPLES =
            key("compiler.delimited-informat.min-line-samples")
                    .defaultValue(2)
                    .withDescription("The minimum number of line samples taken by the compiler for delimited inputs. The samples" +
                            " are used to estimate the number of records. This value can be overridden for a specific input with the" +
                            " input format’s parameters");

    /**
     * The maximal length of a line sample that the compiler takes for delimited inputs. If the length of a single
     * sample exceeds this value (possible because of misconfiguration of the parser), the sampling aborts. This value
     * can be overridden for a specific input with the input format’s parameters.
     */
    public static final ConfigOption<Integer> DELIMITED_FORMAT_MAX_SAMPLE_LEN =
            key("compiler.delimited-informat.max-sample-len")
                    .defaultValue(2097152)
                    .withDescription("The maximal length of a line sample that the compiler takes for delimited inputs. If the" +
                            " length of a single sample exceeds this value (possible because of misconfiguration of the parser)," +
                            " the sampling aborts. This value can be overridden for a specific input with the input format’s" +
                            " parameters.");
}
