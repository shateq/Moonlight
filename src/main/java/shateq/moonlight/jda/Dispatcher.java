package shateq.moonlight.jda;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shateq.moonlight.cmd.Command;
import shateq.moonlight.cmd.CommandContext;
import shateq.moonlight.cmd.CommandWrapper;
import shateq.moonlight.cmd.OrderMeta;
import shateq.moonlight.jda.cmd.PingCmd;

import java.util.*;
import java.util.regex.Pattern;

public final class Dispatcher {
    private static final Logger LOG = LoggerFactory.getLogger("Commands");
    private static final Collection<CommandWrapper> cmd = new LinkedList<>();

    private final Map<String, Command> commands = new HashMap<>();
    private final Map<String, String> aliases = new HashMap<>();

    public Dispatcher() {
        LOG.info("Loading commands...");

        register(PingCmd.class);
//        register(new HelpCmd());
//        register(new ModuleCmd());
//        register(new InformationCmd());
//        register(new PingCmd());
//
//        register(new EchoCmd());
//        register(new GoogleCmd());
//
//        register(new JoinCmd());
//        register(new LeaveCmd());
//        register(new PlayCmd());
        LOG.info("{} is the amount of commands registered.", commands.size());
    }

//    public Map<String, SlashCommandReference> slashCommands() {
//        return Collections.unmodifiableMap(slashCommands);
//    }

    public static void execute(@NotNull MessageReceivedEvent e) {
        final Message msg = e.getMessage();

        try {
            // Do it
            final String[] split = e.getMessage().getContentRaw().trim()
                .replaceFirst("(?i)" + Pattern.quote(MoonlightBot.Const.PREFIX), "")
                .split("\\s+");

            final CommandWrapper cmd = getCommand(split[0].toLowerCase());
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

    private static <T> @NotNull T newOne(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to instantiate " + clazz, e);
        }
    }

    @Nullable
    public static CommandWrapper getCommand(final @NotNull String name) {
        final String search = name.toLowerCase();

        for (CommandWrapper c : cmd) {
            if (c.getName().equals(search) || c.getAliases().contains(search)) {
                return c;
            }
        }
        return null;
    }

    public <T extends Command> Command getCommand(@NotNull Class<T> clazz) {
        var name = clazz.getDeclaredAnnotation(OrderMeta.Name.class);
        return this.commands.get(name.value());
    }

    @Contract(pure = true)
    public @NotNull @UnmodifiableView Map<String, Command> commands() {
        return Collections.unmodifiableMap(commands);
    }

    public <T extends Command> void register(Class<T> clazz) {
        Command instance = newOne(clazz);
        //Annotation[] ann = clazz.getDeclaredAnnotations();
        var name = clazz.getDeclaredAnnotation(OrderMeta.Name.class);
        var aliases = clazz.getDeclaredAnnotation(OrderMeta.Aliases.class);

        this.commands.putIfAbsent(name.value(), instance);
        if (aliases != null) {
            for (String value : aliases.value()) {
                this.aliases.putIfAbsent(value, name.value());
            }
        }
    }

    public void register(@NotNull Command command) {
        Class<? extends Command> clazz = command.getClass();

        var name = clazz.getDeclaredAnnotation(OrderMeta.Name.class);
        var aliases = clazz.getDeclaredAnnotation(OrderMeta.Aliases.class);

        this.commands.putIfAbsent(name.value(), command);
        if (aliases != null) {
            for (String value : aliases.value()) {
                this.aliases.putIfAbsent(value, name.value());
            }
        }
    }
}
