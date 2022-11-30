package shateq.java.moonlight;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.jar.Manifest;

public class MoonlightBot {
    public static final int NORMAL = 0xfffda6; // Casual color
    public static final int BAD = 0xff776b; // Bad color
    public static final String GITHUB_URL = "https://github.com/shateq/Moonlight";
    public static final String USER_AGENT = "Moonlight/@version@/JDA (" + GITHUB_URL + ")";
    private static final Logger LOG = LoggerFactory.getLogger(MoonlightBot.class);
    private static final Dotenv dotenv = Dotenv.load();
    public static Date startedAt;
    public static String VERSION;
    private static MoonlightBot inst;
    private static JDA jda;

    static {
        try {
            final Manifest mf = new Manifest();
            mf.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/MANIFEST.MF"));
            VERSION = mf.getMainAttributes().getValue("Implementation-Version");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        inst = new MoonlightBot();
        startedAt = new Date();
        LOG.info("Process started!");

        jda = JDABuilder.createDefault(MoonlightBot.get("token"), GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_VOICE_STATES).setMemberCachePolicy(MemberCachePolicy.ALL)
            .disableCache(EnumSet.of(CacheFlag.CLIENT_STATUS, CacheFlag.ACTIVITY, CacheFlag.EMOJI))
            .setActivity(Activity.competing("🌕 " + get("prefix") + "pomoc"))
            .addEventListeners(new ListenerAdapter() {
                // onReady
                @Override
                public void onReady(final @NotNull ReadyEvent e) {
                    LOG.info("Logged in as {}!", e.getJDA().getSelfUser().getAsTag());
                }
            }, new ListenerAdapter() {
                // onGuildJoin
                @Override
                public void onGuildJoin(@NotNull GuildJoinEvent e) {
                    LOG.info("New guild appeared! - \"{}\"", e.getGuild().getName());
                }
            })
            .addEventListeners(new MoonlightBot.Listener())
            .build();

        ModuleLauncher.init();
        CommandHandler.init();
    }

    public static String get(final @NotNull String key) {
        return dotenv.get(key.toUpperCase());
    }

    public static MoonlightBot getInstance() {
        return inst;
    }

    public static JDA getJDA() {
        return jda;
    }

    private static class Listener extends ListenerAdapter {

        //        @Override
//        public void onSlashCommand(final @NotNull SlashCommandEvent e) {
//            if (e.getName().equals("say")) {
//                e.reply(e.getOption("content").getAsString()).queue(); // reply immediately
//            }
//        }
        @Override
        public void onMessageReceived(final @NotNull MessageReceivedEvent e) {
            if (e.getAuthor().isBot() || e.isWebhookMessage()) return;
            final Message msg = e.getMessage();
            if (msg.getMentions().getMentions(Message.MentionType.USER).contains(jda.getSelfUser())) {
                enum Vowel {a, e, i, o, u, y}
                e.getChannel()
                    .sendMessage("P" + Vowel.values()[ThreadLocalRandom.current().nextInt(Vowel.values().length)] + "ng! 🏓")
                    .setMessageReference(msg).queue();
            }
            if (msg.getContentRaw().startsWith(get("prefix"))) {
                CommandHandler.genericCommand(e);
            }
        }
    }
}
