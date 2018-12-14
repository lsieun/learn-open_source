package lsieun.flink.configuration;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import lsieun.flink.annotation.Public;

/**
 * This class contains all constants for the configuration. That includes the configuration keys and
 * the default values.
 */
@Public
@SuppressWarnings("unused")
public final class ConfigConstants {

    /**
     * The default timeout for filesystem stream opening: infinite (means max long milliseconds).
     */
    public static final int DEFAULT_FS_STREAM_OPENING_TIMEOUT = 0;

    /**
     * The config parameter defining the timeout for filesystem stream opening.
     * A value of 0 indicates infinite waiting.
     */
    public static final String FS_STREAM_OPENING_TIMEOUT_KEY = "taskmanager.runtime.fs_timeout";

    // ----------------------------- Environment Variables ----------------------------

    /** The environment variable name which contains the location of the configuration directory. */
    public static final String ENV_FLINK_CONF_DIR = "FLINK_CONF_DIR";

    /** The environment variable name which contains the location of the lib folder. */
    public static final String ENV_FLINK_LIB_DIR = "FLINK_LIB_DIR";

    /** The environment variable name which contains the location of the bin directory. */
    public static final String ENV_FLINK_BIN_DIR = "FLINK_BIN_DIR";

    /** The environment variable name which contains the Flink installation root directory. */
    public static final String ENV_FLINK_HOME_DIR = "FLINK_HOME";

    // ---------------------------- Encoding ------------------------------

    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * Not instantiable.
     */
    private ConfigConstants() {}
}
