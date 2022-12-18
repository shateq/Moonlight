package shateq.moonlight.mod;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.ModuleChute;
import shateq.moonlight.util.Identifier;

/**
 * Module abstraction, ListenerAdapter
 */
public abstract class Module extends ListenerAdapter {
    public final String name, id;
    public final Status status;

    public Module(@NotNull Identifier id, Status status) {
        this.name = id.getName();
        this.id = id.getId();
        this.status = status;
    }

    public void init() {
        if (!works()) return;
        ModuleChute.coverage.info(id + " works!");
    }

    public boolean works() {
        return status.equals(Status.ON) || status.equals(Status.BUILT);
    }

    public enum Status {
        ON("🟢", "Włączone"), BUILT("🔵", "Wbudowane"),
        OFF("🔴", "Wyłaczone"), WAITING("🟡", "Oczekiwane"),
        SPECIAL("🟠", "Specjalne");

        public final String mark, legend;

        Status(String mark, String legend) {
            this.mark = mark;
            this.legend = legend;
        }
    }
}
