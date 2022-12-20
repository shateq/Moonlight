package shateq.moonlight.dispatcher;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shateq.moonlight.MoonlightBot;
import shateq.moonlight.cmd.HelpCmd;
import shateq.moonlight.cmd.InfoCmd;
import shateq.moonlight.cmd.ModulesCmd;
import shateq.moonlight.cmd.PingCmd;
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

        register(PingCmd.class);
        register(ModulesCmd.class);
        register(new HelpCmd());
        register(new InfoCmd());
        log.info("{} is the amount of COMMANDS registered.", COMMANDS.size());
    }

    /**
     * Process a command
     *
     * @param event ReceivedEvent
     */
    public static void process(@NotNull MessageReceivedEvent event) {
        String label = event.getMessage().getContentRaw().trim();

        var quote = Pattern.quote(MoonlightBot.Const.PREFIX);
        String[] arguments = label.replaceFirst("(?i)" + quote, "")
            .trim().split("\\s+");

        Command cmd = getCommand(arguments[0]);
        if (cmd != null) {
            execute(cmd, Arrays.copyOfRange(arguments, 1, arguments.length), event);
        } else {
            //TODO fuzzy
            String name = arguments[0];
            Messages.Replies.just("Nieprawidłowe polecenie, brak wyników dla `" + name + "`.", event);
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
        } catch (Exception e) {
            event.getChannel().sendMessage("💀").queue();
            log.error(e.toString());
        }
    }

    /**
     * @param name Command's key
     * @return Command from COMMANDS list
     */
    @Nullable
    public static Command getCommand(@NotNull String name) {
        String search = name.toLowerCase(Locale.ROOT);

        var perName = COMMANDS.get(search);
        if (perName != null) {
            return perName;
        }
        String aliasName = aliases.get(search);
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
