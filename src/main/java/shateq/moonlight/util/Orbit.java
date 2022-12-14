package shateq.moonlight.util;

import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.MoonlightBot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Utilities of the utilities
 */
public final class Orbit {
    public static final Pattern urlPattern = Pattern.compile("((?:https?:\\/\\/)?(?:www.)?(([a-zA-Z0-9-]){2,}\\.){1,4}([a-zA-Z]){2,6}(\\/([a-zA-Z-_\\/\\.0-9#:?=&;,]*)?)?)");
    public static final String skull = "\uD83D\uDC80";

    /**
     * @param string String to be searched
     * @return true if string complains the URL pattern
     */
    public static boolean complainsURL(String string) {
        return urlPattern.matcher(string).find();
    }

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
     * @return Empty String array
     */
    @Contract(value = " -> new", pure = true)
    public static String @NotNull [] noArgs() {
        return new String[]{};
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

    /**
     * @param string Content
     * @return Wrapped String
     */
    @Contract(pure = true)
    public static @NotNull String code(String string) {
        return "```" + string + "```\n";
    }

    public static @NotNull EmbedBuilder colourEmbed(boolean ok) {
        return new EmbedBuilder().setColor(ok ? MoonlightBot.Const.NORMAL : MoonlightBot.Const.BAD);
    }
}
