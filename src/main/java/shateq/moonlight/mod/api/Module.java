package shateq.moonlight.mod.api;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.ModuleChute;

/**
 * Module abstraction, ListenerAdapter
 */
public abstract class Module extends ListenerAdapter {
    public final String name, id;
    public final ModuleStatus status;

    public Module(@NotNull Identifier id, ModuleStatus status) {
        this.name = id.getName();
        this.id = id.getId();
        this.status = status;
    }

    public void init() {
        if (!works()) return;
        ModuleChute.coverage.info(id + " works!");
    }

    public boolean works() {
        return status.equals(ModuleStatus.ON) || status.equals(ModuleStatus.BUILT);
    }
}
