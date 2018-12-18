package lsieun.flink.core.io;


import lsieun.flink.annotation.PublicEvolving;

/**
 * This interface is implemented by classes that provide a version number. Versions numbers can be used to differentiate
 * between evolving classes.
 */
@PublicEvolving
public interface Versioned {

    /**
     * Returns the version number of the object. Versions numbers can be used to differentiate evolving classes.
     */
    int getVersion();
}
