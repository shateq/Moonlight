package shateq.moonlight.cmd;

import net.dv8tion.jda.api.entities.MessageEmbed;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.MoonlightBot;
import shateq.moonlight.dispatcher.Dispatcher;
import shateq.moonlight.dispatcher.GuildContext;
import shateq.moonlight.dispatcher.SlashContext;
import shateq.moonlight.dispatcher.api.Category;
import shateq.moonlight.dispatcher.api.Command;
import shateq.moonlight.dispatcher.api.Order;
import shateq.moonlight.util.Orbit;
import shateq.moonlight.util.Reply;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

@Order(value = "help", note = "Peek all of the commands.")
@Order.Aliases({"h", "pomoc"})
public class HelpCmd implements Command {
    @Override
    public void execute(@NotNull GuildContext c) {
        if (c.args() == null || c.args().length == 0)
            sortedView(
                () -> Reply.A.just("Nie zarejestrowano żadnych komend.", c.event()).queue(),
                embed -> Reply.A.embed(embed, c.event()).queue()
            );
        else
            detailedView( //TODO THIS IS SO BAD
                c.args()[0],
                () -> Reply.A.quote("Brak wyników.", c.event()).queue(),
                embed -> Reply.A.embed(embed, c.event()).queue()
            );
    }

    @Override
    public void slash(@NotNull SlashContext c) {
        // TODO IT IS ALSO SO BAD
        if (c.options().isEmpty() || c.options().size() == 0)
            sortedView(
                () -> c.event().reply("Nie zarejestrowano żadnych komend.").queue(),
                embed -> c.event().replyEmbeds(embed).queue()
            );
        else if (c.event().getOption("search") == null) return;
        detailedView(
            c.event().getOption("search").getAsString(),
            () -> c.event().reply("Brak wyników.").queue(),
            embed -> c.event().replyEmbeds(embed).queue()
        );
    }

    private void detailedView(String search, Runnable noData, @NotNull Consumer<MessageEmbed> send) {
        Command cmd = Dispatcher.getCommand(search);
        if (cmd == null) {
            noData.run();
            return;
        }

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
        var embed = Reply.A.coloredEmbed(true)
            .setTitle("• " + name + str)
            .setDescription(Orbit.code("example") + explanation)
            .setFooter(group)
            .build();

        send.accept(embed);
    }

    private void sortedView(Runnable noData, @NotNull Consumer<MessageEmbed> send) {
        var commands = MoonlightBot.dispatcher().commands().values();
        if (commands.size() == 0) {
            noData.run();
            return;
        }
        var embed = Reply.A.coloredEmbed(true)
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
        send.accept(embed.build());
    }
}
