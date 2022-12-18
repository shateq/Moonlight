package shateq.moonlight;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shateq.moonlight.cmd.funky.PlayCmd;
import shateq.moonlight.cmd.funky.StopCmd;
import shateq.moonlight.mod.Module;
import shateq.moonlight.mod.*;
import shateq.moonlight.util.Identifier;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static shateq.moonlight.mod.FakeModule.nothing;

/**
 * Module registry and accessor
 */
public final class ModuleChute {
    public static final Logger coverage = LoggerFactory.getLogger("ModuleChute");
    private static final Map<String, Module> MODULES = new HashMap<>();

    public ModuleChute() {
        coverage.info("Loading mods...");
        var fishing = new FishingMod(new Identifier("Rybactwo", "fishing"), ModuleStatus.WAITING);
        var boost = new Boost(new Identifier("Ulepszenia", "boost"), ModuleStatus.WAITING);
        var detection = new Detection(new Identifier("Detekcja linków", "detect"), ModuleStatus.OFF);

        List<Module> mods = List.of(
            FakeModule.built(new Identifier("Grunt", "core"), nothing()),
            FakeModule.special(new Identifier("Muzyka", "music"), () -> {
                MoonlightBot.dispatcher().register(PlayCmd.class);
                MoonlightBot.dispatcher().register(StopCmd.class);
            }),
            boost,
            fishing,
            detection
        );

        for (Module mod : mods) {
            setOff(mod);
        }
        coverage.info("Registered {} mods.", MODULES.size());
    }

    /**
     * Prepare module for its dependents
     *
     * @param mod Module
     */
    public void setOff(Module mod) {
        MODULES.putIfAbsent(mod.id, mod);
        if (mod.works()) mod.init();
    }

    /**
     * Module list
     *
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
