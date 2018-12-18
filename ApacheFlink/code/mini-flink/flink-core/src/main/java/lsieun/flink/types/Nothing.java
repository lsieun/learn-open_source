package lsieun.flink.types;

import lsieun.flink.annotation.Public;

 /**
 * A type for (synthetic) operators that do not output data. For example, data sinks.
 */
@Public
public class Nothing {
	private Nothing() {}
}
