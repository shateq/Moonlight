package shateq.moonlight;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shateq.moonlight.dispatcher.Dispatcher;
import shateq.moonlight.mod.ModuleChute;
import shateq.moonlight.music.JukeboxManager;
import shateq.moonlight.util.ThreadFactoryImpl;

import java.io.IOException;
import java.util.EnumSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.jar.Manifest;

import static net.dv8tion.jda.api.requests.GatewayIntent.SCHEDULED_EVENTS;
import static net.dv8tion.jda.api.requests.GatewayIntent.*;
import static net.dv8tion.jda.api.utils.cache.CacheFlag.*;

public final class MoonlightBot {
    public static final Logger LOG = LoggerFactory.getLogger("Main"),
        GUILDS = LoggerFactory.getLogger("Guild");
    private static MoonlightBot inst;

    private final ExecutorService threadPool = Executors.newCachedThreadPool(new ThreadFactoryImpl("Moonlight-Thread-%d"));
    private final JukeboxManager jukeboxManager;
    private final ModuleChute moduleChute;
    private final Dispatcher dispatcher;
    private final JDA jda;

    private MoonlightBot() throws Exception {
        inst = this;
        EnumSet<GatewayIntent> intents =
            EnumSet.of(GUILD_MEMBERS, GUILD_VOICE_STATES, MESSAGE_CONTENT, GUILD_MESSAGES, SCHEDULED_EVENTS);
        EnumSet<CacheFlag> disabledCache =
            EnumSet.of(ACTIVITY, CLIENT_STATUS, EMOJI, FORUM_TAGS, ONLINE_STATUS, STICKER, ROLE_TAGS);

        var listener = new ListeningWire(threadPool);
        jda = JDABuilder.createDefault(System.getProperty("bot_token"), intents).setActivity(Activity.competing("Ping me!"))
            .setAutoReconnect(true)
            .setChunkingFilter(ChunkingFilter.NONE)
            .setMemberCachePolicy(MemberCachePolicy.BOOSTER)
            .disableCache(disabledCache)
            .enableCache(EnumSet.of(VOICE_STATE))
            .addEventListeners(listener)
            .build().awaitReady();
        // Commands are static - one, but there are also not-static module-related commands per-guild.
        dispatcher = new Dispatcher();
        moduleChute = new ModuleChute();
        jukeboxManager = new JukeboxManager();

        dispatcher.updateCommands(jda);
    }

    public static void main(String[] args) {
        LOG.info("Moonlight version: {}, Runtime version: {}", Constant.VERSION, Runtime.version().toString());
        if (System.getenv("bot_token") != null) System.setProperty("bot_token", System.getenv("bot_token"));

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> LOG.error("Uncaught exception in thread {}", t.getName(), e));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdown(0)));
        try {
            new MoonlightBot();
        } catch (Exception e) {
            LOG.error(e.toString());
        }
    }

    /**
     * @return Gateway!
     */
    public static MoonlightBot it() {
        return inst;
    }

    public static ModuleChute moduleChute() {
        return it().moduleChute;
    }

    public static Dispatcher dispatcher() {
        return it().dispatcher;
    }

    public static JDA jda() {
        return it().jda;
    }

    public static JukeboxManager jukebox() {
        return it().jukeboxManager;
    }

    private static ExecutorService executor() {
        return it().threadPool;
    }

    public static void shutdown(int code) {
        inst.jda.getRegisteredListeners().forEach(inst.jda::removeEventListener);
        inst.jda.shutdownNow();
        System.exit(code);
    }

    public static final class Constant {
        public static final int NORMAL = 0xfffda6, BAD = 0xff776b, MUSIC = 0x52c7a0;
        public static final String PREFIX = "->", GITHUB_URL = "https://github.com/shateq/Moonlight";
        public static String VERSION = "@version@";

        static {
            try {
                Manifest mf = new Manifest();
                mf.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/MANIFEST.MF"));
                VERSION = mf.getMainAttributes().getValue("Implementation-Version");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
