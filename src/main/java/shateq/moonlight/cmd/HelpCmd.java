package shateq.moonlight.cmd;

import org.jetbrains.annotations.NotNull;
import shateq.moonlight.MoonlightBot;
import shateq.moonlight.dispatcher.Command;
import shateq.moonlight.dispatcher.Dispatcher;
import shateq.moonlight.dispatcher.GuildContext;
import shateq.moonlight.dispatcher.Order;
import shateq.moonlight.util.Replies;

@Order("help")
@Order.Aliases({"h", "pomoc"})
@Order.Explanation("Check commands")
public class HelpCmd implements Command {
    @Override
    public void execute(@NotNull GuildContext c) {
        if (c.args().isEmpty()) {
            sortedView(c);
        } else {
            explainedView(c);
        }
    }

    private void explainedView(@NotNull GuildContext c) {
        Command cmd = Dispatcher.getCommand(c.args().get(0));
        if (cmd == null) {
            Replies.simply("> Brak wyników.", c.event()).queue();
            return;
        }

        String name = Command.name(cmd);

        StringBuilder str = new StringBuilder();
        var aliases = Command.aliases(cmd);

        assert aliases != null;
        if (!aliases.isEmpty()) {
            for (String a : aliases) {
                str.append(" /").append(a);
            }
        }

        String explanation = Command.explanation(cmd);
        var embed = Replies.authoredEmbed(c.sender(), true)
            .setTitle("• " + name + str)
            .setDescription(explanation)
            .build();
        Replies.embed(embed, c.event()).queue();
    }

    private void sortedView(@NotNull GuildContext c) {
        var commands = MoonlightBot.dispatcher().commands();

        String blank = cookList(Category.Blank);
        String games = cookList(Category.Games);
        String music = cookList(Category.Music);

        var list = Replies.authoredEmbed(c.sender(), true)
            .setTitle("• Pomoc (" + commands.size() + ")")
            .setDescription(blank)
            .addField(Category.Games.title, games, false)
            .addField(Category.Music.title, music, false)
            .build();

        Replies.embed(list, c.event()).queue();
    }

    private @NotNull String cookList(Category category) {
        StringBuilder list = new StringBuilder();
        var group = MoonlightBot.dispatcher().commands().values();

        if (group.size() == 0) {
            return "No commands";
        }

        group.stream().map(Command::name).forEach(
            it -> list.append("`").append(it).append("`")
        );
        return list.toString();
    }
}
