package shateq.java.moonlight;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.util.Date;
import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private static JDA jda;
    public static Date startedAt;

    public static void main(String[] args) throws LoginException {
        LOG.info("Process started!");
        startedAt = new Date();

        jda = JDABuilder.createDefault(Main.get("token"), GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_VOICE_STATES).setMemberCachePolicy(MemberCachePolicy.ALL)
                .disableCache(EnumSet.of(CacheFlag.CLIENT_STATUS, CacheFlag.ACTIVITY, CacheFlag.EMOTE))
                .setActivity(Activity.competing("🌕 "+get("prefix")+"pomoc"))
                .addEventListeners(new ListenerAdapter() {
                    // onReady
                    @Override public void onReady(final @NotNull ReadyEvent e) {
                        LOG.info("Logged in as {}!", e.getJDA().getSelfUser().getAsTag());}}, new ListenerAdapter() {
                    // onGuildJoin
                    @Override public void onGuildJoin(@NotNull GuildJoinEvent e) {
                        LOG.info("New guild appeared! - \"{}\"", e.getGuild().getName());}
                })
                .addEventListeners(new Main.Listener())
                .build();

        ModuleLauncher.init();
        CommandHandler.init();
    }

    private static final Dotenv dotenv = Dotenv.load();
    public static String get(final @NotNull String key) {
        return dotenv.get(key.toUpperCase());
    }

    private static class Listener extends ListenerAdapter {

//        @Override
//        public void onSlashCommand(final @NotNull SlashCommandEvent e) {
//            if (e.getName().equals("say")) {
//                e.reply(e.getOption("content").getAsString()).queue(); // reply immediately
//            }
//        }

        @Override
        public void onGuildMessageReceived(final @NotNull MessageReceivedEvent e) {
            if (e.getAuthor().isBot() || e.isWebhookMessage()) return;
            final Message msg = e.getMessage();

            if(msg.getMentions().getMentions(Message.MentionType.USER).contains(jda.getSelfUser())) {
                enum Vowel {a, e, i, o, u, y}
                e.getChannel().sendMessage("P" + Vowel.values()[ThreadLocalRandom.current().nextInt(Vowel.values().length)] + "ng! 🏓").reference(msg).queue();
            }

            if (msg.getContentRaw().startsWith(get("prefix"))) {
                CommandHandler.genericCommand(e);
            }
        }
    }

    public Main getInstance() {
        return this;
    }

    public static JDA getJDA() {
        return jda;
    }
}
