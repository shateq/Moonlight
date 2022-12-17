package shateq.moonlight.dispatcher;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

/**
 * Command abstraction
 */
public interface Command {
    @Contract(pure = true)
    static @Nullable String name(@NotNull Command cmd) {
        Class<? extends Command> clazz = cmd.getClass();
        var name = clazz.getDeclaredAnnotation(Order.class);
        if (name != null) return name.value();
        return null;
    }

    static @Nullable String explanation(@NotNull Command cmd) {
        Class<? extends Command> clazz = cmd.getClass();
        var name = clazz.getDeclaredAnnotation(Order.Explanation.class);
        if (name != null) return name.value();
        return null;
    }

    static @Nullable @Unmodifiable List<String> aliases(@NotNull Command cmd) {
        Class<? extends Command> clazz = cmd.getClass();
        var name = clazz.getDeclaredAnnotation(Order.Aliases.class);
        if (name != null) return List.of(name.value());
        return null;
    }

    static boolean hidden(@NotNull Command cmd) {
        Class<? extends Command> clazz = cmd.getClass();
        return clazz.getDeclaredAnnotation(Order.Hidden.class) != null;
    }

    // TODO annotation
    void execute(@NotNull GuildContext c) throws Exception;

    enum Category {
        Blank(""),
        Slash("Slash"),
        Music("Muzyka"), Games("Gry");

        public final String title;

        Category(final String title) {
            this.title = title;
        }
    }
}
