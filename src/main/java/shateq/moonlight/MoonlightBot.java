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
import shateq.moonlight.util.Orbit;

import java.io.IOException;
import java.util.EnumSet;
import java.util.jar.Manifest;

import static net.dv8tion.jda.api.requests.GatewayIntent.SCHEDULED_EVENTS;
import static net.dv8tion.jda.api.requests.GatewayIntent.*;
import static net.dv8tion.jda.api.utils.cache.CacheFlag.*;

public final class MoonlightBot {
    public static final Logger LOGGER = LoggerFactory.getLogger("Moonlight Main");
    private static MoonlightBot inst;
    //Fields
    public final ModuleChute moduleChute;
    public final Dispatcher dispatcher;
    public final JDA jda;

    private MoonlightBot(ListenerAdapter listener) throws Exception {
        inst = this;
        EnumSet<GatewayIntent> intents =
            EnumSet.of(GUILD_MEMBERS, GUILD_VOICE_STATES, MESSAGE_CONTENT, GUILD_MESSAGES, SCHEDULED_EVENTS);
        EnumSet<CacheFlag> disabledCache =
            EnumSet.of(ACTIVITY, CLIENT_STATUS, EMOJI, FORUM_TAGS, ONLINE_STATUS, STICKER, ROLE_TAGS);

        jda = JDABuilder.createDefault(System.getProperty("bot_token"), intents).setActivity(Activity.competing("Ping me!"))
            .setAutoReconnect(true)
            .setChunkingFilter(ChunkingFilter.ALL)
            .setMemberCachePolicy(MemberCachePolicy.BOOSTER)
            .disableCache(disabledCache)
            .addEventListeners(listener)
            .build().awaitReady();

        dispatcher = new Dispatcher();
        moduleChute = new ModuleChute();
    }

    public static void main(String[] args) {
        LOGGER.info("Runtime version: {}", Runtime.version().toString());

        if (Orbit.env("bot_token") != null) System.setProperty("bot_token", Orbit.env("bot_token"));

        try {
            new MoonlightBot(new ListeningWire());
        } catch (Exception e) {
            LOGGER.error(e.toString());
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

    public static void shutdown() {
        inst.jda.shutdown();
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
