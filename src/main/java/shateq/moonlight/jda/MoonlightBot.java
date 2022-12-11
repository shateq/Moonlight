package shateq.moonlight.jda;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shateq.moonlight.dispatcher.Dispatcher;

import java.io.IOException;
import java.util.EnumSet;
import java.util.jar.Manifest;

public final class MoonlightBot {
    public static final Logger log = LoggerFactory.getLogger("Moonlight Main");
    private static MoonlightBot inst;
    //Fields
    private final ModuleChute moduleChute;
    public final Dispatcher dispatcher;
    public final JDA jda;

    private MoonlightBot() throws Exception {
        inst = this;
        EnumSet<GatewayIntent> intents =
            EnumSet.of(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.SCHEDULED_EVENTS);
        EnumSet<CacheFlag> cache = EnumSet.of(CacheFlag.CLIENT_STATUS, CacheFlag.ACTIVITY, CacheFlag.EMOJI,
            CacheFlag.STICKER, CacheFlag.VOICE_STATE);

        jda = JDABuilder.createDefault(MoonlightBot.env("bot_token"), intents)
            .setAutoReconnect(true)
            .setChunkingFilter(ChunkingFilter.ALL)
            .setMemberCachePolicy(MemberCachePolicy.BOOSTER)
            .disableCache(cache)

            .setActivity(Activity.competing("Ping me!"))
            .addEventListeners(new ListeningWire())
            .build().awaitReady();

        moduleChute = new ModuleChute();
        dispatcher = new Dispatcher();
    }

    public static void main(String[] args) {
        log.warn("Started!");

        try {
            new MoonlightBot();
        } catch (Exception e) {
            log.error(String.valueOf(e));
            System.exit(1);
        }
    }

    public static MoonlightBot it() {
        return inst;
    }

    public static String env(@NotNull String key) {
        return System.getenv(key);
    }

    public static ModuleChute modules() {
        return it().moduleChute;
    }

    public static Dispatcher dispatcher() {
        return it().dispatcher;
    }

    public JDA jda() {
        return jda;
    }

    public static final class Const {
        public static final int NORMAL = 0xfffda6; // Casual color
        public static final int BAD = 0xff776b; // Bad color
        public static final String PREFIX = "->";
        public static final String GITHUB_URL = "https://github.com/shateq/Moonlight";
        public static String VERSION;

        static {
            try {
                final Manifest mf = new Manifest();
                mf.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/MANIFEST.MF"));
                VERSION = mf.getMainAttributes().getValue("Implementation-Version");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
