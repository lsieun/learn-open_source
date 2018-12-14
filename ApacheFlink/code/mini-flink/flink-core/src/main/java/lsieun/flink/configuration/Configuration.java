package lsieun.flink.configuration;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lsieun.flink.annotation.Public;
import lsieun.flink.api.common.ExecutionConfig;

/**
 * Lightweight configuration object which stores key/value pairs.
 */
@Public
public class Configuration extends ExecutionConfig.GlobalJobParameters
        implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;


    /** The log object used for debugging. */
    private static final Logger LOG = LoggerFactory.getLogger(Configuration.class);

    /** Stores the concrete key/value pairs of this configuration object. */
    protected final HashMap<String, Object> confData;

    // --------------------------------------------------------------------------------------------

    /**
     * Creates a new empty configuration.
     */
    public Configuration() {
        this.confData = new HashMap<>();
    }

    /**
     * Creates a new configuration with the copy of the given configuration.
     *
     * @param other The configuration to copy the entries from.
     */
    public Configuration(Configuration other) {
        this.confData = new HashMap<>(other.confData);
    }

    // --------------------------------------------------------------------------------------------

    /**
     * Returns the class associated with the given key as a string.
     *
     * @param <T> The type of the class to return.

     * @param key The key pointing to the associated value
     * @param defaultValue The optional default value returned if no entry exists
     * @param classLoader The class loader used to resolve the class.
     *
     * @return The value associated with the given key, or the default value, if to entry for the key exists.
     */
    @SuppressWarnings("unchecked")
    public <T> Class<T> getClass(String key, Class<? extends T> defaultValue, ClassLoader classLoader) throws ClassNotFoundException {
        Object o = getRawValue(key);
        if (o == null) {
            return (Class<T>) defaultValue;
        }

        if (o.getClass() == String.class) {
            return (Class<T>) Class.forName((String) o, true, classLoader);
        }

        LOG.warn("Configuration cannot evaluate value " + o + " as a class name");
        return (Class<T>) defaultValue;
    }

    /**
     * Adds the given key/value pair to the configuration object. The class can be retrieved by invoking
     * {@link #getClass(String, Class, ClassLoader)} if it is in the scope of the class loader on the caller.
     *
     * @param key The key of the pair to be added
     * @param klazz The value of the pair to be added
     * @see #getClass(String, Class, ClassLoader)
     */
    public void setClass(String key, Class<?> klazz) {
        setValueInternal(key, klazz.getName());
    }

    /**
     * Returns the value associated with the given key as a boolean.
     *
     * @param key
     *        the key pointing to the associated value
     * @param defaultValue
     *        the default value which is returned in case there is no value associated with the given key
     * @return the (default) value associated with the given key
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        Object o = getRawValue(key);
        if (o == null) {
            return defaultValue;
        }

        return convertToBoolean(o);
    }

    /**
     * Adds the given key/value pair to the configuration object.
     *
     * @param key
     *        the key of the key/value pair to be added
     * @param value
     *        the value of the key/value pair to be added
     */
    public void setBoolean(String key, boolean value) {
        setValueInternal(key, value);
    }

    /**
     * Returns the value associated with the given key as an integer.
     *
     * @param key
     *        the key pointing to the associated value
     * @param defaultValue
     *        the default value which is returned in case there is no value associated with the given key
     * @return the (default) value associated with the given key
     */
    public int getInteger(String key, int defaultValue) {
        Object o = getRawValue(key);
        if (o == null) {
            return defaultValue;
        }

        return convertToInt(o, defaultValue);
    }

    /**
     * Adds the given key/value pair to the configuration object.
     *
     * @param key
     *        the key of the key/value pair to be added
     * @param value
     *        the value of the key/value pair to be added
     */
    public void setInteger(String key, int value) {
        setValueInternal(key, value);
    }

    /**
     * Returns the value associated with the given key as a long.
     *
     * @param key
     *        the key pointing to the associated value
     * @param defaultValue
     *        the default value which is returned in case there is no value associated with the given key
     * @return the (default) value associated with the given key
     */
    public long getLong(String key, long defaultValue) {
        Object o = getRawValue(key);
        if (o == null) {
            return defaultValue;
        }

        return convertToLong(o, defaultValue);
    }

    /**
     * Adds the given key/value pair to the configuration object.
     *
     * @param key
     *        the key of the key/value pair to be added
     * @param value
     *        the value of the key/value pair to be added
     */
    public void setLong(String key, long value) {
        setValueInternal(key, value);
    }

    /**
     * Returns the value associated with the given key as a float.
     *
     * @param key
     *        the key pointing to the associated value
     * @param defaultValue
     *        the default value which is returned in case there is no value associated with the given key
     * @return the (default) value associated with the given key
     */
    public float getFloat(String key, float defaultValue) {
        Object o = getRawValue(key);
        if (o == null) {
            return defaultValue;
        }

        return convertToFloat(o, defaultValue);
    }

    /**
     * Adds the given key/value pair to the configuration object.
     *
     * @param key
     *        the key of the key/value pair to be added
     * @param value
     *        the value of the key/value pair to be added
     */
    public void setFloat(String key, float value) {
        setValueInternal(key, value);
    }

    /**
     * Returns the value associated with the given key as a double.
     *
     * @param key
     *        the key pointing to the associated value
     * @param defaultValue
     *        the default value which is returned in case there is no value associated with the given key
     * @return the (default) value associated with the given key
     */
    public double getDouble(String key, double defaultValue) {
        Object o = getRawValue(key);
        if (o == null) {
            return defaultValue;
        }

        return convertToDouble(o, defaultValue);
    }

    /**
     * Adds the given key/value pair to the configuration object.
     *
     * @param key
     *        the key of the key/value pair to be added
     * @param value
     *        the value of the key/value pair to be added
     */
    public void setDouble(String key, double value) {
        setValueInternal(key, value);
    }

    /**
     * Returns the value associated with the given key as a byte array.
     *
     * @param key
     *        The key pointing to the associated value.
     * @param defaultValue
     *        The default value which is returned in case there is no value associated with the given key.
     * @return the (default) value associated with the given key.
     */
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    public byte[] getBytes(String key, byte[] defaultValue) {

        Object o = getRawValue(key);
        if (o == null) {
            return defaultValue;
        }
        else if (o.getClass().equals(byte[].class)) {
            return (byte[]) o;
        }
        else {
            LOG.warn("Configuration cannot evaluate value {} as a byte[] value", o);
            return defaultValue;
        }
    }

    /**
     * Adds the given byte array to the configuration object. If key is <code>null</code> then nothing is added.
     *
     * @param key
     *        The key under which the bytes are added.
     * @param bytes
     *        The bytes to be added.
     */
    public void setBytes(String key, byte[] bytes) {
        setValueInternal(key, bytes);
    }

    /**
     * Returns the value associated with the given key as a string.
     *
     * @param key
     *        the key pointing to the associated value
     * @param defaultValue
     *        the default value which is returned in case there is no value associated with the given key
     * @return the (default) value associated with the given key
     */
    public String getString(String key, String defaultValue) {
        Object o = getRawValue(key);
        if (o == null) {
            return defaultValue;
        } else {
            return o.toString();
        }
    }

    /**
     * Adds the given key/value pair to the configuration object.
     *
     * @param key
     *        the key of the key/value pair to be added
     * @param value
     *        the value of the key/value pair to be added
     */
    public void setString(String key, String value) {
        setValueInternal(key, value);
    }


    // --------------------------------------------------------------------------------------------

    /**
     * Returns the keys of all key/value pairs stored inside this
     * configuration object.
     *
     * @return the keys of all key/value pairs stored inside this configuration object
     */
    public Set<String> keySet() {
        synchronized (this.confData) {
            return new HashSet<>(this.confData.keySet());
        }
    }

    /**
     * Checks whether there is an entry with the specified key.
     *
     * @param key key of entry
     * @return true if the key is stored, false otherwise
     */
    public boolean containsKey(String key){
        synchronized (this.confData){
            return this.confData.containsKey(key);
        }
    }

    /**
     * Adds all entries in this {@code Configuration} to the given {@link Properties}.
     */
    public void addAllToProperties(Properties props) {
        synchronized (this.confData) {
            for (Map.Entry<String, Object> entry : this.confData.entrySet()) {
                props.put(entry.getKey(), entry.getValue());
            }
        }
    }


    public void addAll(Configuration other) {
        synchronized (this.confData) {
            synchronized (other.confData) {
                this.confData.putAll(other.confData);
            }
        }
    }

    // --------------------------------------------------------------------------------------------

    @Override
    public Configuration clone() {
        Configuration config = new Configuration();
        config.addAll(this);

        return config;
    }

    @Override
    public Map<String, String> toMap() {
        synchronized (this.confData){
            Map<String, String> ret = new HashMap<>(this.confData.size());
            for (Map.Entry<String, Object> entry : confData.entrySet()) {
                ret.put(entry.getKey(), entry.getValue().toString());
            }
            return ret;
        }
    }

    // --------------------------------------------------------------------------------------------

    private Object getRawValue(String key) {
        if (key == null) {
            throw new NullPointerException("Key must not be null.");
        }

        synchronized (this.confData) {
            return this.confData.get(key);
        }
    }

    <T> void setValueInternal(String key, T value) {
        if (key == null) {
            throw new NullPointerException("Key must not be null.");
        }
        if (value == null) {
            throw new NullPointerException("Value must not be null.");
        }

        synchronized (this.confData) {
            this.confData.put(key, value);
        }
    }

    // --------------------------------------------------------------------------------------------
    //  Type conversion
    // --------------------------------------------------------------------------------------------

    private int convertToInt(Object o, int defaultValue) {
        if (o.getClass() == Integer.class) {
            return (Integer) o;
        }
        else if (o.getClass() == Long.class) {
            long value = (Long) o;
            if (value <= Integer.MAX_VALUE && value >= Integer.MIN_VALUE) {
                return (int) value;
            } else {
                LOG.warn("Configuration value {} overflows/underflows the integer type.", value);
                return defaultValue;
            }
        }
        else {
            try {
                return Integer.parseInt(o.toString());
            }
            catch (NumberFormatException e) {
                LOG.warn("Configuration cannot evaluate value {} as an integer number", o);
                return defaultValue;
            }
        }
    }

    private long convertToLong(Object o, long defaultValue) {
        if (o.getClass() == Long.class) {
            return (Long) o;
        }
        else if (o.getClass() == Integer.class) {
            return ((Integer) o).longValue();
        }
        else {
            try {
                return Long.parseLong(o.toString());
            }
            catch (NumberFormatException e) {
                LOG.warn("Configuration cannot evaluate value " + o + " as a long integer number");
                return defaultValue;
            }
        }
    }

    private boolean convertToBoolean(Object o) {
        if (o.getClass() == Boolean.class) {
            return (Boolean) o;
        }
        else {
            return Boolean.parseBoolean(o.toString());
        }
    }

    private float convertToFloat(Object o, float defaultValue) {
        if (o.getClass() == Float.class) {
            return (Float) o;
        }
        else if (o.getClass() == Double.class) {
            double value = ((Double) o);
            if (value == 0.0
                    || (value >= Float.MIN_VALUE && value <= Float.MAX_VALUE)
                    || (value >= -Float.MAX_VALUE && value <= -Float.MIN_VALUE)) {
                return (float) value;
            } else {
                LOG.warn("Configuration value {} overflows/underflows the float type.", value);
                return defaultValue;
            }
        }
        else {
            try {
                return Float.parseFloat(o.toString());
            }
            catch (NumberFormatException e) {
                LOG.warn("Configuration cannot evaluate value {} as a float value", o);
                return defaultValue;
            }
        }
    }

    private double convertToDouble(Object o, double defaultValue) {
        if (o.getClass() == Double.class) {
            return (Double) o;
        }
        else if (o.getClass() == Float.class) {
            return ((Float) o).doubleValue();
        }
        else {
            try {
                return Double.parseDouble(o.toString());
            }
            catch (NumberFormatException e) {
                LOG.warn("Configuration cannot evaluate value {} as a double value", o);
                return defaultValue;
            }
        }
    }

    // --------------------------------------------------------------------------------------------

    @Override
    public int hashCode() {
        int hash = 0;
        for (String s : this.confData.keySet()) {
            hash ^= s.hashCode();
        }
        return hash;
    }

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        else if (obj instanceof Configuration) {
            Map<String, Object> otherConf = ((Configuration) obj).confData;

            for (Map.Entry<String, Object> e : this.confData.entrySet()) {
                Object thisVal = e.getValue();
                Object otherVal = otherConf.get(e.getKey());

                if (!thisVal.getClass().equals(byte[].class)) {
                    if (!thisVal.equals(otherVal)) {
                        return false;
                    }
                } else if (otherVal.getClass().equals(byte[].class)) {
                    if (!Arrays.equals((byte[]) thisVal, (byte[]) otherVal)) {
                        return false;
                    }
                } else {
                    return false;
                }
            }

            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.confData.toString();
    }
}
