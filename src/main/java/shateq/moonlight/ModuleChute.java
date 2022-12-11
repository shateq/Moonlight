package shateq.moonlight;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shateq.moonlight.modules.Module;
import shateq.moonlight.modules.*;
import shateq.moonlight.util.Identifier;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Module registry and accessor
 */
public final class ModuleChute {
    private static final Logger log = LoggerFactory.getLogger("ModuleChute");
    private static final Map<String, Module> MODULES = new HashMap<>();

    public ModuleChute() {
        log.info("Loading mods...");

        setOff(FakeModule.built(new Identifier("Grunt", "core")));
        setOff(FakeModule.off(new Identifier("Muzyka", "music")));

        setOff(new FishingMod(new Identifier("Rybactwo", "fishing"), Module.Status.SPECIAL));
        setOff(new Boost(new Identifier("Ulepszenia", "boost"), Module.Status.WAITING));
        setOff(new Detection(new Identifier("Detekcja linków", "detect"), Module.Status.OFF));
        log.info("Registered {} mods.", MODULES.size());
    }

    /**
     * Prepare module for its dependents
     * @param mod Module
     */
    public void setOff(Module mod) {
        MODULES.putIfAbsent(mod.id, mod);
    }

    /**
     * Module list
     * @return Id-module map
     */
    @Contract(pure = true)
    public @NotNull @UnmodifiableView Map<String, Module> modules() {
        return Collections.unmodifiableMap(MODULES);
    }

    /**
     * @param id Module identifier
     * @return Module from MODULES list
     */
    @Nullable
    public Module getModule(@NotNull String id) {
        return MODULES.get(id);
    }
}
