package shateq.moonlight.dispatcher;

import kotlin.NotImplementedError;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shateq.moonlight.MoonlightBot;
import shateq.moonlight.cmd.*;
import shateq.moonlight.dispatcher.api.ArgumentException;
import shateq.moonlight.dispatcher.api.Command;
import shateq.moonlight.dispatcher.api.Order;
import shateq.moonlight.util.Messages;
import shateq.moonlight.util.Orbit;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Command execution coordination
 */
public final class Dispatcher {
    private static final Logger log = LoggerFactory.getLogger("CommandDispatcher");
    private static final Map<String, Command> COMMANDS = new HashMap<>();
    private static final Map<String, String> aliases = new HashMap<>(); //alias point to name

    public Dispatcher() {
        log.info("Loading COMMANDS...");

        MoonlightBot.jda().updateCommands().addCommands(
            Commands.slash("welp", "Reply back with options.")
                .addOption(OptionType.BOOLEAN, "panic", "Whether to panic", false)
        ).queue();

        List.of(
            new PingCmd(),
            new ModulesCmd(),
            new HelpCmd(),
            new InfoCmd(),
            new HejCmd()
        ).forEach(this::register);
        log.info("{} is the amount of COMMANDS registered.", COMMANDS.size());
    }

    /**
     * Receive a slash command
     * @param event Slash Command Interaction
     */
    public static void slash(@NotNull SlashCommandInteractionEvent event) {
        if (event.getInteraction().getName().equals("welp")) {
            event.getInteraction().reply("Here is it!").setEphemeral(true).queue();
        }
    }

    /**
     * Process a command
     *
     * @param event ReceivedEvent
     */
    public static void process(@NotNull MessageReceivedEvent event) {
        String label = event.getMessage().getContentRaw().trim();
        if (!label.startsWith(MoonlightBot.Const.PREFIX)) return; // PREFIX FIRST

        var quote = Pattern.quote(MoonlightBot.Const.PREFIX);
        String[] arguments = label.replaceFirst("(?i)" + quote, "")
            .trim().split("\\s+");

        String name = arguments[0];
        Command cmd = getCommand(name);
        if (cmd != null) {
            execute(cmd, Arrays.copyOfRange(arguments, 1, arguments.length), event);
        } else {
            Messages.Replies.just("`" + name + "` : brak wyników. Użyj sobie `->h`.", event).queue();
        }
    }

    /**
     * Execute and pass context
     *
     * @param event Context root
     */
    public static void execute(Command cmd, String[] args, @NotNull MessageReceivedEvent event) {
        GuildContext context = new GuildContext(args, event, event.getAuthor());

        Member self = event.getGuild().getSelfMember();
        try {
            cmd.execute(context);
        } catch (NotImplementedError ie) {
            Messages.Replies.reference("Operacja nie została zaimplementowana!", event).queue();
        } catch (ArgumentException ae) {
            Messages.Replies.just(ae.getMessage(), event).queue();
        } catch (Exception e) {
            Messages.Replies.just("💀", event).queue();
            log.error(e.toString());
        }
    }

    /**
     * Use the static {@code execute}, no need to specify new empty string array
     *
     * @param event Context root
     */
    public static void executeNoArgs(Command cmd, @NotNull MessageReceivedEvent event) {
        execute(cmd, new String[]{}, event);
    }

    /**
     * @param name Command's key
     * @return Command from COMMANDS list
     */
    @Nullable
    public static Command getCommand(@NotNull String name) {
        String search = name.toLowerCase(Locale.ROOT), aliasName;

        var perName = COMMANDS.get(search);
        if (perName != null) {
            return perName;
        }
        aliasName = aliases.get(search);
        return COMMANDS.get(aliasName);
    }

    /**
     * @param clazz Command class
     * @return Command from COMMANDS list
     */
    @Nullable
    public <T extends Command> Command getCommand(@NotNull Class<T> clazz) {
        var name = clazz.getDeclaredAnnotation(Order.class);
        return COMMANDS.get(name.value());
    }

    /**
     * @return Name-command map
     */
    @Contract(pure = true)
    public @NotNull @UnmodifiableView Map<String, Command> commands() {
        return Collections.unmodifiableMap(COMMANDS);
    }

    /**
     * Registers command by its class
     *
     * @param clazz Command class
     */
    public <T extends Command> void register(Class<T> clazz) {
        this.register(Orbit.newOne(clazz));
    }

    /**
     * Registers command by its instance
     *
     * @param command Command object
     */
    public void register(@NotNull Command command) {
        Class<? extends Command> clazz = command.getClass();
        //Annotation[] ann = clazz.getDeclaredAnnotations();
        var name = clazz.getDeclaredAnnotation(Order.class);
        var aliasList = clazz.getDeclaredAnnotation(Order.Aliases.class);

        COMMANDS.putIfAbsent(name.value(), command);
        if (aliasList != null) {
            for (String value : aliasList.value()) {
                aliases.putIfAbsent(value, name.value());
            }
        }
    }
}
