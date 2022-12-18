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
        var order = cmd.getClass().getDeclaredAnnotation(Order.class);
        if (order != null) return order.value();
        return null;
    }

    static @NotNull Category category(@NotNull Command cmd) {
        var rank = cmd.getClass().getDeclaredAnnotation(Order.Rank.class);
        return rank != null ? rank.value() : Category.General;
    }

    static @Nullable String explanation(@NotNull Command cmd) {
        var explain = cmd.getClass().getDeclaredAnnotation(Order.Explanation.class);
        if (explain != null) return explain.value();
        return null;
    }

    static @Nullable String example(@NotNull Command cmd) {
        var example = cmd.getClass().getDeclaredAnnotation(Order.Example.class);
        if (example != null) return example.value();
        return null;
    }

    static @Nullable @Unmodifiable List<String> aliases(@NotNull Command cmd) {
        cmd.getClass().getDeclaredAnnotation(Order.Aliases.class).value();
        var aliases = cmd.getClass().getDeclaredAnnotation(Order.Aliases.class);
        if (aliases != null) return List.of(aliases.value());
        return null;
    }

    static boolean hidden(@NotNull Command cmd) {
        return cmd.getClass().getDeclaredAnnotation(Order.Hidden.class) != null;
    }

    // TODO annotation
    void execute(@NotNull GuildContext c) throws Exception;
}
