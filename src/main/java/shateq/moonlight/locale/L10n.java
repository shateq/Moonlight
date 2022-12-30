package shateq.moonlight.locale;


import net.dv8tion.jda.api.interactions.DiscordLocale;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;

import static net.dv8tion.jda.api.interactions.DiscordLocale.*;


/**
 * Localization per guild {@see net.dv8tion.jda.api.interactions.DiscordLocale}
 */
public class L10n {
    public static DiscordLocale FALLBACK = ENGLISH_US;

    @Contract(pure = true)
    public static @NotNull String literal(String key, DiscordLocale locale) {
        locale = establishLocale(locale);
        // if key missing in locale then use one from engrish
        return "translated from file";
    }

    protected static DiscordLocale establishLocale(DiscordLocale locale) {
        if (locale == UNKNOWN) {
            return FALLBACK;
        }

        var name = locale.getLocale().replaceAll("-", "_");
        if (!Files.exists(Path.of(name))) {
            return FALLBACK;
        }
        return locale;
    }
}
