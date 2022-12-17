package shateq.moonlight.modules;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.util.Identifier;

public class FakeModule extends Module {
    public FakeModule(Identifier id, Status status, @NotNull Runnable arrow) {
        super(id, status);
        arrow.run();
    }

    @Contract("_, _ -> new")
    public static @NotNull Module built(Identifier id, @NotNull Runnable arrow) {
        return new FakeModule(id, Status.BUILT, arrow);
    }

    public static @NotNull Module off(Identifier id) {
        return new FakeModule(id, Status.OFF, () -> {
        });
    }
}
