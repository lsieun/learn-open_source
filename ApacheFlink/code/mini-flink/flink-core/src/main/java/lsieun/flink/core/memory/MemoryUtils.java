package lsieun.flink.core.memory;


import java.lang.reflect.Field;
import java.nio.ByteOrder;

import lsieun.flink.annotation.Internal;

/**
 * Utility class for memory operations.
 */
@Internal
public class MemoryUtils {

    /** The "unsafe", which can be used to perform native memory accesses. */
    @SuppressWarnings("restriction")
    public static final sun.misc.Unsafe UNSAFE = getUnsafe();

    /** The native byte order of the platform on which the system currently runs. */
    public static final ByteOrder NATIVE_BYTE_ORDER = ByteOrder.nativeOrder();

    @SuppressWarnings("restriction")
    private static sun.misc.Unsafe getUnsafe() {
        try {
            Field unsafeField = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            return (sun.misc.Unsafe) unsafeField.get(null);
        } catch (SecurityException e) {
            throw new RuntimeException("Could not access the sun.misc.Unsafe handle, permission denied by security manager.", e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("The static handle field in sun.misc.Unsafe was not found.");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Bug: Illegal argument reflection access for static field.", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Access to sun.misc.Unsafe is forbidden by the runtime.", e);
        } catch (Throwable t) {
            throw new RuntimeException("Unclassified error while trying to access the sun.misc.Unsafe handle.", t);
        }
    }

    /** Should not be instantiated. */
    private MemoryUtils() {}
}
