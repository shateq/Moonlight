package shateq.moonlight.modules;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.util.Identifier;

/**
 * Module abstraction, ListenerAdapter
 */
public abstract class Module extends ListenerAdapter {
    public final String name;
    public final String id;
    public final Status status;

    public Module(@NotNull Identifier id, Status status) {
        this.name = id.name();
        this.id = id.id();
        this.status = status;
    }

    public void init() {
        if (this.status.equals(Status.OFF)) return;
    }

    public boolean works() {
        return status.equals(Status.ON) || status.equals(Status.BUILT);
    }

    public enum Status {
        ON("🟢"), OFF("🔴"), WAITING("🟡"), BUILT("🔵"), SPECIAL("🟠");

        public final String mark;

        Status(final String mark) {
            this.mark = mark;
        }
    }
}
