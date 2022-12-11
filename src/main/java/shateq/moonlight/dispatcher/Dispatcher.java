package shateq.moonlight.dispatcher;

import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shateq.moonlight.cmd.HelpCmd;
import shateq.moonlight.cmd.ModulesCmd;
import shateq.moonlight.cmd.PingCmd;
import shateq.moonlight.jda.MoonlightBot;

import java.util.*;
import java.util.regex.Pattern;

public final class Dispatcher {
    private static final Logger LOG = LoggerFactory.getLogger("Commands");
    private static final Map<String, Command> commands = new HashMap<>();
    private static final Map<String, String> aliases = new HashMap<>(); //alias point to name

    public Dispatcher() {
        LOG.info("Loading commands...");

        register(PingCmd.class);
        register(ModulesCmd.class);
        register(new HelpCmd());
        LOG.info("{} is the amount of commands registered.", commands.size());
    }

    /*public Map<String, SlashCommandReference> slashCommands() {
        return Collections.unmodifiableMap(slashCommands);
    }*/

    public static void execute(@NotNull MessageReceivedEvent e) {
        String message = e.getMessage().getContentRaw().trim();

        String[] arguments = message.replaceFirst("(?i)" + Pattern.quote(MoonlightBot.Const.PREFIX), "")
            .split("\\s+");

        Command cmd = getCommand(arguments[0]);
        if (cmd == null) {
            return;
        }

        List<String> args = List.of(arguments).subList(1, arguments.length);
        GuildContext context = new GuildContext(args, e, e.getAuthor());

        SelfUser self = e.getJDA().getSelfUser();

        try {
            cmd.execute(context);
        } catch (Exception ex) {
            e.getChannel().sendMessage("💥").queue();
            LOG.error(ex.toString());
        }
    }

    public static <T> @NotNull T newOne(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to instantiate " + clazz, e);
        }
    }

    @Nullable
    public static Command getCommand(@NotNull String name) {
        String search = name.toLowerCase(Locale.ROOT);

        var perName = commands.get(search);
        if (perName != null) {
            return perName;
        }

        String aliasName = aliases.get(search);
        return commands.get(aliasName);
    }

    @Nullable
    public <T extends Command> Command getCommand(@NotNull Class<T> clazz) {
        var name = clazz.getDeclaredAnnotation(Order.class);
        return commands.get(name.value());
    }

    @Contract(pure = true)
    public @NotNull @UnmodifiableView Map<String, Command> commands() {
        return Collections.unmodifiableMap(commands);
    }

    public <T extends Command> void register(Class<T> clazz) {
        this.register(newOne(clazz));
    }

    public void register(@NotNull Command command) {
        Class<? extends Command> clazz = command.getClass();
        //Annotation[] ann = clazz.getDeclaredAnnotations();
        var name = clazz.getDeclaredAnnotation(Order.class);
        var aliasList = clazz.getDeclaredAnnotation(Order.Aliases.class);

        commands.putIfAbsent(name.value(), command);
        if (aliasList != null) {
            for (String value : aliasList.value()) {
                aliases.putIfAbsent(value, name.value());
            }
        }
    }
}
