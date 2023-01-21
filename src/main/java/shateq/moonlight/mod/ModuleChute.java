package shateq.moonlight.mod;

import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import shateq.moonlight.MoonlightBot;
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
    // TODO moduleSet type?
    private static final Map<String, Module> MODULES = new HashMap<>();
    private final Map<Long, Map<String, Module>> moduleChutes;

    public ModuleChute() {
        moduleChutes = new HashMap<>();

        //TODO this constructor maps modules to ids so every guild can "GET" module ids to load into the modulechutes
        MoonlightBot.LOG.info("Mapping modules...");

        var fishing = new FishingMod(new Identifier("Rybactwo", "fishing"), ModuleStatus.ON);
        var boost = new BoostMod(new Identifier("Ulepszenia", "boost"), ModuleStatus.WAITING);
        var detection = new DetectionMod(new Identifier("Detekcja linków", "detect"), ModuleStatus.ON);
        var polish = new DetectionMod(new Identifier("Polska Literatura", "lit"), ModuleStatus.OFF);

        List.of(
            FakeModule.built(new Identifier("Grunt", "core"), nothing()),
            FakeModule.built(new Identifier("Muzyka", "music"),
                () -> Set.of(
                    Play.class, Leave.class,
                    Pause.class, Playlist.class,
                    Skip.class, Repeat.class,
                    Destroy.class, Shuffle.class,
                    NowPlaying.class
                ).forEach(MoonlightBot.dispatcher()::register)),
            boost,
            fishing,
            detection,
            polish
        ).forEach(this::setOff);

        MoonlightBot.LOG.info("Mapped {} mods.", MODULES.size());
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

    public Module fetch(@NotNull Guild guild, String id) {
        return moduleChutes.get(guild.getIdLong()).get(id);
    }

    public void rubOut(@NotNull Guild guild) {
        moduleChutes.get(guild.getIdLong()).clear();
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
