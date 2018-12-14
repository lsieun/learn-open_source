package lsieun.flink.api.java.io;

import lsieun.flink.annotation.PublicEvolving;
import lsieun.flink.api.common.io.DelimitedInputFormat;

/**
 * Input Format that reads text files. Each line results in another element.
 */
@PublicEvolving
public class TextInputFormat extends DelimitedInputFormat<String> {
}
