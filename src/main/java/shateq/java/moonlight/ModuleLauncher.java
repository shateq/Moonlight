package shateq.java.moonlight;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shateq.java.moonlight.mods.BoostMd;
import shateq.java.moonlight.mods.CleverMd;
import shateq.java.moonlight.mods.DetectionMd;
import shateq.java.moonlight.mods.FishingMd;
import shateq.java.moonlight.util.Module;
import shateq.java.moonlight.util.Status;

import java.util.Collection;
import java.util.LinkedList;

public final class ModuleLauncher {
    private static final Collection<Module> mods = new LinkedList<>();
    private static final Logger LOG = LoggerFactory.getLogger(CommandHandler.class);

    // Initialization
    public static void init() {
        LOG.info("Loading modules..");
        add(fakeModule("basic", "Podstawy", Status.BUILT));
        add(fakeModule("music", "Komendy muzyczne", Status.BUILT));
        add(new BoostMd(new Module.Settings().identifier("boost").name("Kontrola ulepszeń serwera"))); // Boost
        add(new CleverMd(new Module.Settings().identifier("clever").name("Rozmowy z SI").status(Status.WAITING))); // Clever Bot
        add(new DetectionMd(new Module.Settings().identifier("detect").name("Wykrywanie linków").status(Status.ON))); // Link Detection
        add(new FishingMd(new Module.Settings().identifier("fishing").name("Rybactwo").status(Status.WAITING))); // Fishing
    }

    // Registry
    private static void add(final Module mod) {
        boolean exists = mods.stream().anyMatch((it) -> it.getId().equalsIgnoreCase(mod.getId()));

        if (exists) {
            throw new IllegalArgumentException("Duplication of module!");
        }
        mods.add(mod);
        mod.start();
    }

    public static Collection<Module> showModules() {
        return mods;
    }

    @Nullable
    public static Module getModule(final String id) {
        for (Module m : mods) {
            if (m.getId().equals(id)) {
                return m;
            }
        }
        return null;
    }

    private static @NotNull Module fakeModule(final String id, final String name, final Status state) {
        return new Module(new Module.Settings().identifier(id).name(name).status(state));
    }
}
