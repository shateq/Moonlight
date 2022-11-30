package shateq.java.moonlight.util;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Module extends ListenerAdapter implements ModuleSkeleton {
    private static String name;
    private static String id;
    private static Status status;

    public Module(Module.Settings settings) {
        name = settings.name;
        id = settings.id;
        status = settings.status;
    }

    // Get NAME
    public static String getName() {
        return name;
    }
    // Get ID
    public static String getId() {
        return id;
    }
    // Get STATUS
    public static Status getStatus() {
        return status;
    }

    public boolean available() {
        return status.equals(Status.ON) || status.equals(Status.BUILT);
    }

    @Override
    public void start() {

    }

    public static class Settings {
        String name;
        String id;
        Status status;

        public Settings() {
            this.status = Status.OFF;
        }

        // Set name
        public Module.Settings name(final String name) {
            this.name = name;
            return this;
        }

        public Module.Settings identifier(final @NotNull String id) {
            this.id = id.toLowerCase();
            return this;
        }

        public Module.Settings status(final Status state) {
            this.status = state;
            return this;
        }

    }
}
