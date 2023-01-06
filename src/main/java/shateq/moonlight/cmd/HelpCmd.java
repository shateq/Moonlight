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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Order(value = "help", note = "Peek all of the commands.")
@Order.Aliases({"h", "pomoc"})
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
            detailedView(c.event().getOption("search").getAsString(), c);
    }

    private void detailedView(String search, CommandContext<?, ?> c) throws ArgumentException {
        Command cmd = Dispatcher.getCommand(search);
        if (cmd == null) throw new ArgumentException("Brak wyników.");


        String name = Command.name(cmd),
            explanation = Command.explanation(cmd),
            group = Objects.requireNonNull(Command.group(cmd)).getTitle();

        StringBuilder str = new StringBuilder();
        var aliases = Command.aliases(cmd);

        if (aliases != null && !aliases.isEmpty()) {
            for (String a : aliases) {
                str.append(" /").append(a);
            }
        }
        var embed = Orbit.colourEmbed(true)
            .setTitle("• " + name + str)
            .setDescription(Orbit.code("example") + explanation)
            .setFooter(group)
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
