package shateq.java.moonlight;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Locale;
import java.util.jar.Manifest;

public final class MoonlightBot {
    public static final Logger log = LoggerFactory.getLogger(MoonlightBot.class);
    private static MoonlightBot inst;
    //Fields
    private final ModuleLauncher modules;
    private final CommandHandler commands;
    private JDA jda;

    private MoonlightBot() throws Exception {
        inst = this;
        EnumSet<GatewayIntent> intents = EnumSet.of(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES);
        EnumSet<CacheFlag> cache = EnumSet.of(CacheFlag.CLIENT_STATUS, CacheFlag.ACTIVITY, CacheFlag.EMOJI);

        jda = JDABuilder.createDefault(MoonlightBot.env("token"), intents)
            .setActivity(Activity.competing("🌕 Ping!"))
            .setMemberCachePolicy(MemberCachePolicy.BOOSTER)
            .disableCache(cache)
            .setAutoReconnect(true)

            .addEventListeners(new ListenerAdapter() {
                // onReady

            }, new ListenerAdapter() {
                // onGuildJoin
                @Override
                public void onGuildJoin(@NotNull GuildJoinEvent e) {
                    log.info("New guild appeared! - \"{}\"", e.getGuild().getName());
                }
            })
            .addEventListeners(new ListeningWire())
            .build().awaitReady();

        modules = new ModuleLauncher();
        commands = new CommandHandler();
    }

    public static void main(String[] args) {
        log.info("Process started!");

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
        return System.getenv(key.toUpperCase(Locale.ROOT));
    }

    public ModuleLauncher modules() {
        return modules;
    }

    public CommandHandler commands() {
        return commands;
    }

    public JDA jda() {
        return jda;
    }

    public static final class Const {
        public static final int NORMAL = 0xfffda6; // Casual color
        public static final int BAD = 0xff776b; // Bad color
        public static final String PREFIX = "->";
        public static final String URL = "https://discord.com/api/oauth2/authorize?client_id=835915266587885629&permissions=8&scope=bot";
        public static final String GITHUB_URL = "https://github.com/shateq/Moonlight";
        public static final String USER_AGENT = "Moonlight/@version@/JDA (" + GITHUB_URL + ")";
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
