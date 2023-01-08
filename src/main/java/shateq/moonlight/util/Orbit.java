package shateq.moonlight.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
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
     * @param name Argument name
     * @param type Argument type
     * @return formatted argument name with option type
     */
    @Contract(pure = true)
    public static @NotNull String typed(String name, @NotNull Class<?> type) {
        return "<" + name + " " + type.getSimpleName() + ">";
    }

    /**
     * See also: {@link Orbit#typed(String, Class)}
     */
    public static @NotNull String typed(String name, @NotNull OptionType optionType) {
        var type = optionType.name().substring(0, 1).toUpperCase() + optionType.name().substring(1).toLowerCase();
        return "<" + name + " " + type + ">";
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

    public static String toTimestamp(long milliseconds)
    {
        int seconds = (int) (milliseconds / 1000) % 60 ;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours   = (int) ((milliseconds / (1000 * 60 * 60)) % 24);

        if (hours > 0)
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        else
            return String.format("%02d:%02d", minutes, seconds);
    }

    public static @NotNull EmbedBuilder colourEmbed(boolean ok) {
        return new EmbedBuilder().setColor(ok ? MoonlightBot.Constant.NORMAL : MoonlightBot.Constant.BAD);
    }

    public static @NotNull EmbedBuilder colourEmbed(int colour) {
        return new EmbedBuilder().setColor(colour);
    }
}
