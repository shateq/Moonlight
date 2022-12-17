package shateq.moonlight.util;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utilities of the utilities
 */
public final class Outer {

    /**
     * Get an environment variable value.
     *
     * @param key Variable name
     * @return Value
     */
    public static String env(@NotNull String key) {
        return System.getenv(key);
    }

    /**
     * Instantiate a class
     *
     * @param clazz Class with zero-argument constructor
     * @param <T>   Object
     * @return New Instance
     */
    public static <T> @NotNull T newOne(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to instantiate " + clazz, e);
        }
    }

    /**
     * Simply format date
     *
     * @param date    Desired date
     * @param pattern Format pattern
     * @return Formatted date string
     */
    public static @NotNull String simpleDateFormat(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }
}
