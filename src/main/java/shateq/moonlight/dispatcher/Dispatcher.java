package shateq.moonlight.dispatcher;

import kotlin.NotImplementedError;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
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
import shateq.moonlight.dispatcher.api.CommandContext;
import shateq.moonlight.dispatcher.api.Order;
import shateq.moonlight.util.Orbit;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Command execution coordination
 */
public final class Dispatcher {
    private static final Logger log = LoggerFactory.getLogger("CommandDispatcher");
    private static final Map<String, Command> COMMANDS = new HashMap<>();
    private static final Map<String, String> aliases = new HashMap<>(); //alias points to name

    private static final Map<String, CommandData> COMMAND_DATA = new HashMap<>(); //map for easy filtering for duplicates

    public Dispatcher() {
        log.info("Loading COMMANDS...");
        List.of(new PingCmd(), new ModulesCmd(), new HelpCmd(), new InfoCmd(), new HejCmd()).forEach(this::register);

        MoonlightBot.jda().updateCommands().addCommands(COMMAND_DATA.values()).complete(); //register COMMAND_DATA
        log.info("{} is the amount of COMMANDS registered.", COMMANDS.size());
    }

    public static void upsertCommandData(CommandData... commandData) {
        for (CommandData data : List.of(commandData))
            COMMAND_DATA.putIfAbsent(data.getName(), data);
    }

    /**
     * Process interaction
     *
     * @param event Slash Command Interaction
     */
    public static void processSlash(@NotNull SlashCommandInteractionEvent event) {
        if (!event.isFromGuild()) return;
        var cmd = getCommand(event.getName());

        if (cmd != null) {
            var context = new SlashContext(event);
            execute(cmd, context);
        }
    }

    /**
     * Process text
     *
     * @param event MessageReceivedEvent from the event listener
     */
    public static void processText(@NotNull MessageReceivedEvent event) {
        String label = event.getMessage().getContentRaw().trim();
        if (!label.startsWith(MoonlightBot.Const.PREFIX)) return; //PREFIX FIRST

        var quote = Pattern.quote(MoonlightBot.Const.PREFIX);
        String[] arguments = label.replaceFirst("(?i)" + quote, "").trim().split("\\s+");

        String name = arguments[0];
        if (name.isBlank()) return; //no annoying 'that'

        Command cmd = getCommand(name);
        if (cmd != null) {
            var guildContext = new GuildContext(event, Arrays.copyOfRange(arguments, 1, arguments.length));
            execute(cmd, guildContext);
        } else {
            event.getMessage().reply("`" + name + "` : brak wynik??w. U??yj sobie `->h`.").queue();
        }
    }

    public static void execute(Command cmd, CommandContext<?, ?> context) {
        try {
            if (context instanceof GuildContext guild) {
                cmd.execute(guild); //the thing
            } else if (context instanceof SlashContext slash) {
                cmd.slash(slash);
            }
        } catch (NotImplementedError ie) {
            var out = "Operacja nie zosta??a zaimplementowana! ";
            if (ie.getMessage() != null) out += ie.getMessage();

            context.reply(out);
        } catch (ArgumentException ae) {
            context.reply(ae.getMessage());
        } catch (Exception e) {
            context.reply(Orbit.skull);
            log.error(e.toString());
        }
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
     * @param clazz Command class
     */
    public <T extends Command> void register(Class<T> clazz) {
        this.register(Orbit.newOne(clazz));
    }

    /**
     * Registers passed instance
     *
     * @param command Command object
     */
    public void register(@NotNull Command command) {
        Class<? extends Command> clazz = command.getClass();
        var name = clazz.getDeclaredAnnotation(Order.class);
        if (name == null) {
            log.error("Command {} could not be registered. MISSING ANNOTATION METADATA!", command);
            return;
        }

        var aliasList = clazz.getDeclaredAnnotation(Order.Aliases.class);

        COMMANDS.putIfAbsent(name.value(), command);
        if (aliasList != null)
            for (String value : aliasList.value())
                aliases.putIfAbsent(value, name.value());
    }
}
