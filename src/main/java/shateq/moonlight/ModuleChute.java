package shateq.moonlight;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import shateq.moonlight.mod.BoostMod;
import shateq.moonlight.mod.DetectionMod;
import shateq.moonlight.mod.FishingMod;
import shateq.moonlight.mod.api.FakeModule;
import shateq.moonlight.mod.api.Identifier;
import shateq.moonlight.mod.api.Module;
import shateq.moonlight.mod.api.ModuleStatus;
import shateq.moonlight.music.*;

import java.util.*;

import static shateq.moonlight.mod.api.FakeModule.nothing;

/**
 * Module registry and accessor
 */
public final class ModuleChute {
    private static final Map<String, Module> MODULES = new HashMap<>();

    public ModuleChute() {
        MoonlightBot.LOG.info("Loading mods...");
        var fishing = new FishingMod(new Identifier("Rybactwo", "fishing"), ModuleStatus.ON);
        var boost = new BoostMod(new Identifier("Ulepszenia", "boost"), ModuleStatus.WAITING);
        var detection = new DetectionMod(new Identifier("Detekcja linków", "detect"), ModuleStatus.OFF);
        var polish = new DetectionMod(new Identifier("Polska Literatura", "lit"), ModuleStatus.OFF);

        List.of(
            FakeModule.built(new Identifier("Grunt", "core"), nothing()),
            FakeModule.built(new Identifier("Muzyka", "music"),
                () -> Set.of(
                    Play.class, Stop.class,
                    Pause.class, Playlist.class,
                    Skip.class, Repeat.class,
                    Destroy.class
                ).forEach(MoonlightBot.dispatcher()::register)),
            boost,
            fishing,
            detection,
            polish
        ).forEach(this::setOff);
        MoonlightBot.LOG.info("Registered {} mods.", MODULES.size());
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
