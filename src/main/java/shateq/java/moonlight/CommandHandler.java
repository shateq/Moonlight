package shateq.java.moonlight;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shateq.java.moonlight.cmd.*;
import shateq.java.moonlight.cmd.music.JoinCmd;
import shateq.java.moonlight.cmd.music.LeaveCmd;
import shateq.java.moonlight.cmd.music.PlayCmd;
import shateq.java.moonlight.util.Category;
import shateq.java.moonlight.util.CommandAdapter;
import shateq.java.moonlight.util.CommandContext;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

public final class CommandHandler {
    private static final String prefix = Main.get("prefix");
    private static final Logger LOG = LoggerFactory.getLogger(CommandHandler.class);

    private static final Collection<CommandAdapter> cmd = new LinkedList<>();
    private static final Collection<CommandAdapter> blank = new LinkedList<>();
    private static final Collection<CommandAdapter> music = new LinkedList<>();
    private static final Collection<CommandAdapter> games = new LinkedList<>();

    public static void init() {
        // Generic
        register(new HelpCmd(), Category.Blank);
        register(new ModuleCmd(), Category.Blank);
        register(new InformationCmd(), Category.Blank);
        register(new PingCmd(), Category.Blank);

        register(new EchoCmd(), Category.Games);
        register(new GoogleCmd(), Category.Games);

        register(new JoinCmd(), Category.Music);
        register(new LeaveCmd(), Category.Music);
        register(new PlayCmd(), Category.Music);
        LOG.info("{} commands have been registered.", cmd.size());
    }

    private static void register(final CommandAdapter command, final Category category) {
        final boolean exists = cmd.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(command.getName()));
        if(exists) {
            throw new IllegalArgumentException("Duplicated command name!");
        }

        LOG.info("Command '{}' registered.", command.getName());
        cmd.add(command);
        switch (category) {
            case Blank -> blank.add(command);
            case Music -> music.add(command);
            case Games -> games.add(command);
        }
    }

    /////////////////
    // Slash Commands
//    public void onSlashCommand(final @NotNull SlashCommandEvent e) {
//        if (e.getName().equals("say")) {
//            e.reply(e.getOption("content").getAsString()).queue(); // reply immediately
//        }
//    }

    ///////////////////
    // Generic Commands
    public static void genericCommand(final @NotNull MessageReceivedEvent e) {
        final Message msg = e.getMessage();

        try {
            // Do it
            final String[] split = e.getMessage().getContentRaw().trim()
                    .replaceFirst("(?i)" + Pattern.quote(prefix), "")
                    .split("\\s+");

            final CommandAdapter cmd = getCommand(split[0].toLowerCase());
            if (cmd != null) {
                e.getChannel().sendTyping().queue();
                final List<String> args = List.of(split).subList(1, split.length);

                CommandContext ctx = new CommandContext(e, args);
                cmd.run(ctx);
            }
            // End it

            LOG.info("{}: {}", e.getAuthor().getAsTag(), msg.getContentRaw());
        } catch (Exception ex) {
            e.getChannel().sendMessage("💥 ☠").queue();
            ex.printStackTrace();
        }
    }

    public static Collection<CommandAdapter> showCommands() {
        return cmd;
    }

    public static Collection<CommandAdapter> showCommands(final @NotNull Category group) {
        switch (group) {
            case Blank -> {
                return blank;
            }
            case Music -> {
                return music;
            }
            case Games -> {
                return games;
            }
        }
        return null;
    }

    @Nullable
    public static CommandAdapter getCommand(final @NotNull String data) {
        final String search = data.toLowerCase();

        for(CommandAdapter c : cmd) {
            if(c.getName().equals(search) || c.getAliases().contains(search)) {
                return c;
            }
        }
        return null;
    }

    // Command Utilities!
    public static void missingArgs(final String help, final GuildMessageReceivedEvent e) {
        commandReply("> Brak wszystkich argumentów.\n**Poprawne użycie:** `"+prefix+help+"`.", e);
    }

    public static void missingPerms(final @NotNull Permission perm, final GuildMessageReceivedEvent e) {
        commandReply("> Brakujące uprawnienia.\n**Kod:** `"+perm.getName()+"`.", e);
    }

    public static void commandReply(final CharSequence msg, final @NotNull GuildMessageReceivedEvent e){
        e.getChannel().sendMessage(msg).reference(e.getMessage()).queue();
    }

    public static void commandEmbed(final MessageEmbed msg, final @NotNull GuildMessageReceivedEvent e) {
        e.getChannel().sendMessageEmbeds(msg).reference(e.getMessage()).queue();
    }
}
