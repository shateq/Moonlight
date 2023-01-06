package shateq.moonlight.dispatcher.api;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import shateq.moonlight.dispatcher.GuildContext;
import shateq.moonlight.dispatcher.SlashContext;

import java.util.List;

/**
 * Command abstraction, constructor may be used as other metadata.
 */
public interface Command {
    @Contract(pure = true)
    static @Nullable Order orders(@NotNull Command cmd) {
        return cmd.getClass().getDeclaredAnnotation(Order.class);
    }

    static @Nullable String name(@NotNull Command cmd) {
        var order = orders(cmd);
        if (order != null) return order.value();
        return null;
    }

    static Category group(@NotNull Command cmd) {
        var order = orders(cmd);
        if (order != null) return order.group();
        return Category.General;
    }

    static String explanation(@NotNull Command cmd) {
        var order = orders(cmd);
        if (order != null) return order.note();
        return "No data";
    }

    static @Unmodifiable List<String> example(@NotNull Command cmd) {
        var example = cmd.getClass().getDeclaredAnnotation(Order.Example.class);
        if (example != null) return List.of(example.value());
        return List.of();
    }

    static @Unmodifiable List<String> aliases(@NotNull Command cmd) {
        var aliases = cmd.getClass().getDeclaredAnnotation(Order.Aliases.class);
        if (aliases != null) return List.of(aliases.value());
        return List.of();
    }

    static boolean hidden(@NotNull Command cmd) {
        return cmd.getClass().getDeclaredAnnotation(Order.Hidden.class) != null;
    }

    void execute(@NotNull GuildContext c) throws Exception;

    default void slash(@NotNull SlashContext c) throws Exception {
        throw new NotImplementedError();
    }
}
