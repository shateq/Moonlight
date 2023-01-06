package shateq.moonlight.cmd;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.MoonlightBot;
import shateq.moonlight.dispatcher.Dispatcher;
import shateq.moonlight.dispatcher.GuildContext;
import shateq.moonlight.dispatcher.SlashContext;
import shateq.moonlight.dispatcher.api.*;
import shateq.moonlight.util.Orbit;

import java.util.*;

@Order(value = "help", note = "Peek all of the commands.")
@Order.Aliases({"h", "pomoc"})
@Order.Example({"help <name String>", "help mods", "help"})
public class HelpCmd implements Command {
    public HelpCmd() {
        Dispatcher.upsertCommandData(Commands.slash("help", "Peek other commands")
            .addOption(OptionType.STRING, "search", "Search for a command to be detailed.")
            .setGuildOnly(true)
        );
    }

    @Override
    public void execute(@NotNull GuildContext c) throws ArgumentException {
        if (c.args() == null || c.args().length == 0)
            sortedView(c);
        else
            detailedView(c.args()[0], c);
    }

    @Override
    public void slash(@NotNull SlashContext c) throws ArgumentException {
        if (c.options().isEmpty() || c.options().size() == 0) {
            sortedView(c);
        } else
            detailedView(Objects.requireNonNull(c.event().getOption("search")).getAsString(), c);
    }

    private void detailedView(String search, CommandContext<?, ?> c) throws ArgumentException {
        Command cmd = Dispatcher.getCommand(search);
        if (cmd == null) throw new ArgumentException("Brak wyników.");
        String name = Command.name(cmd);

        StringBuilder aliasEntry = new StringBuilder(),
            examplesEntry = new StringBuilder();

        List<String> aliases = Command.aliases(cmd),
            examples = Command.example(cmd);

        if (aliases != null && !aliases.isEmpty())
            for (String a : aliases)
                aliasEntry.append(" \\").append(a);

        if (examples != null && !examples.isEmpty())
            for (String e : examples)
                examplesEntry.append(e).append("\n");

        var ex = !examplesEntry.toString().isBlank() ? Orbit.code(examplesEntry.toString()) : "";
        var embed = Orbit.colourEmbed(true)
            .setTitle("• " + name + aliasEntry)
            .setDescription(Command.explanation(cmd) + "\n" + ex)
            .setFooter(Command.group(cmd).getTitle())
            .build();

        c.replyEmbeds(embed);
    }

    private void sortedView(CommandContext<?, ?> c) throws ArgumentException {
        var commands = MoonlightBot.dispatcher().commands().values();
        if (commands.size() == 0) throw new ArgumentException("Nie zarejestrowano żadnych komend.");

        var embed = Orbit.colourEmbed(true)
            .setTitle("• Pomoc (" + commands.stream().filter(it -> !Command.hidden(it)).toList().size() + ")");

        Arrays.stream(Category.values()).forEach(category -> {
            StringBuilder builder = new StringBuilder();
            Set<Command> group = new HashSet<>();

            commands.forEach(it -> {
                if (Command.hidden(it)) return;
                if (Objects.equals(Command.group(it), category))
                    group.add(it);
            });

            group.forEach(command -> builder.append("`").append(Command.name(command)).append("` "));
            if (!builder.isEmpty())
                embed.addField(category.getTitle(), builder.toString(), false);
        });

        c.replyEmbeds(embed.build());
    }
}
