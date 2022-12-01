package shateq.moonlight.util;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helpers {
    private static final Pattern url = Pattern.compile("((?:https?:\\/\\/)?(?:www.)?(([a-zA-Z0-9-]){2,}\\.){1,4}([a-zA-Z]){2,6}(\\/([a-zA-Z-_\\/\\.0-9#:?=&;,]*)?)?)");

    public static boolean checkURL(final String s) {
        Matcher match = url.matcher(s);
        return match.find();
    }

    public static @NotNull String humanDate(final Date date, final String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }
}
