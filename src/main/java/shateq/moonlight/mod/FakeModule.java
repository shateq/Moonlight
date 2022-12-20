package shateq.moonlight.mod;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.util.Identifier;

public final class FakeModule extends Module {
    public FakeModule(Identifier id, ModuleStatus status, @NotNull Runnable arrow) {
        super(id, status);
        arrow.run();
    }

    @Contract("_, _ -> new")
    public static @NotNull Module built(Identifier id, @NotNull Runnable arrow) {
        return new FakeModule(id, ModuleStatus.BUILT, arrow);
    }

    public static @NotNull Module off(Identifier id) {
        return new FakeModule(id, ModuleStatus.OFF, () -> {
        });
    }

    @Contract(pure = true)
    public static @NotNull Runnable nothing() {
        return () -> {
        };
    }
}
