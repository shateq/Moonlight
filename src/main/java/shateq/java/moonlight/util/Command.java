package shateq.java.moonlight.util;

import java.util.List;

public class Command {
    private final String name;
    private final List<String> aliases;

    public Command(Command.Config config) {
        this.name = config.name;
        this.aliases = config.aliases;
    }

    public static class Config {
        String name;
        List<String> aliases;

        public Config() {
            this.aliases = List.of();
        }

        public enum Type {
            Generic, Slash, Both
        }
    }
}
