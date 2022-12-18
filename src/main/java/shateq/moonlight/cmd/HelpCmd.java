package shateq.moonlight.cmd;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.MoonlightBot;
import shateq.moonlight.dispatcher.*;
import shateq.moonlight.util.Util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Order("help")
@Order.Aliases({"h", "pomoc"})
@Order.Explanation("Check commands")
public class HelpCmd implements Command {
    @Contract(pure = true)
    public static @NotNull String code(String string) {
        return "```" + string + "```\n";
    }

    @Override
    public void execute(@NotNull GuildContext c) {
        if (c.args() == null || c.args().isEmpty())
            sortedView(c);
        else
            explainedView(c);
    }

    private void explainedView(@NotNull GuildContext c) {
        Command cmd = Dispatcher.getCommand(c.args().get(0));
        if (cmd == null) {
            Util.Replies.quote("Brak wyników.", c.event()).queue();
            return;
        }
        String name = Command.name(cmd), explanation = Command.explanation(cmd);

        StringBuilder str = new StringBuilder();
        var aliases = Command.aliases(cmd);

        assert aliases != null;
        if (!aliases.isEmpty()) {
            for (String a : aliases) {
                str.append(" /").append(a);
            }
        }

        var embed = Util.Replies.coloredEmbed(true).setTitle("• " + name + str).setDescription(code("example") + explanation).build();
        Util.Replies.embed(embed, c.event()).queue();
    }

    private void sortedView(@NotNull GuildContext c) {
        var commands = MoonlightBot.dispatcher().commands().values();
        if (commands.size() == 0) {
            Util.Replies.simply("Nie zarejestrowano komend.", c.event());
            return;
        }
        var embed = Util.Replies.coloredEmbed(true).setTitle("• Pomoc (" + commands.size() + ")");

        Arrays.stream(Category.values()).forEach(category -> {
            StringBuilder builder = new StringBuilder();
            Set<Command> group = new HashSet<>();

            commands.forEach(it -> {
                if (Command.category(it).equals(category)) group.add(it);
            });

            group.forEach(command -> builder.append("`").append(Command.name(command)).append("` "));
            embed.addField(category.title, builder.toString(), false);
        });

        Util.Replies.embed(embed.build(), c.event()).queue();
    }
}
