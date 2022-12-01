package shateq.moonlight.modules;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.util.Identifier;

public abstract class Module extends ListenerAdapter {
    public final String name;
    public final String id;
    public final Status status;

    public Module(@NotNull Identifier id, Status status) {
        this.name = id.name();
        this.id = id.name();
        this.status = status;
    }



    public boolean works() {
        return status.equals(Status.ON) || status.equals(Status.BUILT);
    }

    public enum Status {
        ON("🟢"), OFF("🟡"), WAITING("🔴"), BUILT("🔵"), SPECIAL("🟠");

        public final String mark;

        Status(final String mark) {
            this.mark = mark;
        }
    }
}
