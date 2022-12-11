package shateq.moonlight.modules;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.util.Identifier;

public class FakeModule extends Module {
    public FakeModule(Identifier id, Status status) {
        super(id, status);
    }

    @Contract("_ -> new")
    public static @NotNull Module built(Identifier id) {
        return new FakeModule(id, Status.BUILT);
    }

    public static @NotNull Module off(Identifier id) {
        return new FakeModule(id, Status.OFF);
    }
}
