package shateq.moonlight.mod.api;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
        return new FakeModule(id, ModuleStatus.OFF, nothing());
    }

    @Contract(pure = true)
    public static @NotNull Runnable nothing() {
        return () -> {
        };
    }
}
