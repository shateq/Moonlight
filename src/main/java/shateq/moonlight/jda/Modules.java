package shateq.moonlight.jda;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shateq.moonlight.modules.Module;
import shateq.moonlight.modules.*;
import shateq.moonlight.util.Identifier;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class Modules {
    private static final Map<String, Module> MODULES = new HashMap<>();
    private static final Logger LOG = LoggerFactory.getLogger("Modules");

    public static Boost BOOST;
    public static Fishing FISHING;
    public static Files FILES;
    public static Detection DETECTION;

    public Modules() {
        LOG.info("Loading modules...");

        add(FakeModule.built(new Identifier("Grunt", "core")));
        add(FakeModule.built(new Identifier("Muzyka", "music")));
        BOOST = new Boost(new Identifier("Ulepszenia", "boost"), Module.Status.WAITING);
        FISHING = new Fishing(new Identifier("Rybactwo", "fishing"), Module.Status.WAITING);
        DETECTION = new Detection(new Identifier("Detekcja linków", "detect"), Module.Status.OFF);
    }

    private static void add(final Module mod) {
        boolean exists = MODULES.values().stream().anyMatch((it) -> it.id.equalsIgnoreCase(mod.id));

        if (exists) {
            throw new IllegalArgumentException("Duplication of module!");
        }
        MODULES.put(mod.id, mod);
    }

    public Map<String, Module> map() {
        return MODULES;
    }

    @Contract(pure = true)
    public @NotNull Collection<Module> showModules() {
        return MODULES.values();
    }

    @Nullable
    public Module getModule(@NotNull String id) {
        for (Module m : MODULES.values()) {
            if (m.id.equals(id)) {
                return m;
            }
        }
        return null;
    }
}
