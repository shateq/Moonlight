package shateq.moonlight;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shateq.moonlight.dispatcher.Dispatcher;
import shateq.moonlight.util.Outer;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.EnumSet;
import java.util.Locale;
import java.util.jar.Manifest;

import static net.dv8tion.jda.api.requests.GatewayIntent.SCHEDULED_EVENTS;
import static net.dv8tion.jda.api.requests.GatewayIntent.*;
import static net.dv8tion.jda.api.utils.cache.CacheFlag.*;

public final class MoonlightBot {
    public static final Logger LOGGER = LoggerFactory.getLogger("Moonlight Main");
    public static Locale LOCALE = Locale.US;
    private static MoonlightBot inst;
    //Fields
    public final ModuleChute moduleChute;
    public final Dispatcher dispatcher;
    public final JDA jda;

    private MoonlightBot(ListenerAdapter listener) throws Exception {
        inst = this;
        EnumSet<GatewayIntent> intents =
            EnumSet.of(GUILD_MEMBERS, GUILD_VOICE_STATES, GUILD_MESSAGES, MESSAGE_CONTENT, SCHEDULED_EVENTS);
        EnumSet<CacheFlag> cache =
            EnumSet.of(ACTIVITY, CLIENT_STATUS, EMOJI, FORUM_TAGS, ONLINE_STATUS, STICKER, ROLE_TAGS);

        jda = JDABuilder.createDefault(Outer.env("bot_token"), intents)
            .setAutoReconnect(true)
            .setChunkingFilter(ChunkingFilter.ALL)
            .setMemberCachePolicy(MemberCachePolicy.BOOSTER)
            .disableCache(cache)

            .setActivity(Activity.competing("Ping me!"))
            .addEventListeners(listener)
            .build().awaitReady();

        dispatcher = new Dispatcher();
        moduleChute = new ModuleChute();
    }

    public static void main(String[] args) {
        LOGGER.info("Runtime version: {}", Runtime.version().toString());
        LOGGER.info("OS: {}, {}", ManagementFactory.getOperatingSystemMXBean().getName(), ManagementFactory.getOperatingSystemMXBean().getArch());
        LOGGER.info(LOCALE.toString());
        try {
            new MoonlightBot(new ListeningWire());
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
        }
    }

    public static MoonlightBot it() {
        return inst;
    }

    public static ModuleChute moduleChute() {
        return inst.moduleChute;
    }

    public static Dispatcher dispatcher() {
        return inst.dispatcher;
    }

    public static JDA jda() {
        return inst.jda;
    }

    public static final class Const {
        public static final int NORMAL = 0xfffda6, BAD = 0xff776b; //Casual colors
        public static final String PREFIX = "->", GITHUB_URL = "https://github.com/shateq/Moonlight";
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
