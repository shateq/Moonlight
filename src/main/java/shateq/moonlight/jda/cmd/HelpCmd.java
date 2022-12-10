package shateq.moonlight.jda.cmd;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.cmd.*;
import shateq.moonlight.jda.MoonlightBot;
import shateq.moonlight.util.Replies;

import java.util.Collection;
import java.util.List;

@OrderMeta.Name("help")
@OrderMeta.Aliases({"h", "pomoc"})
public class HelpCmd extends Command implements CommandWrapper {

    @Override
    public void run(@NotNull CommandContext ctx) {
        final List<String> args = ctx.args();
        final User author = ctx.event().getAuthor();

        if (args.isEmpty()) {
            final Collection<Command> commands = MoonlightBot.dispatcher().commands().values();
            final String blank = cookList(Category.Blank);
            final String games = cookList(Category.Games);
            final String music = cookList(Category.Music);

            EmbedBuilder list = new EmbedBuilder().setColor(MoonlightBot.Const.NORMAL).setTitle("• Pomoc (" + commands.size() + " komend)").setAuthor(author.getAsTag(), null, author.getEffectiveAvatarUrl())
                .setDescription(blank).addField(Category.Games.title, games, false).addField(Category.Music.title, music, false);

            Replies.commandEmbed(list.build(), ctx.event());
            return;
        }

        final String query = args.get(0);
        CommandWrapper cmd = MoonlightBot.dispatcher().getCommand(query);
        if (cmd == null) {
            Replies.commandReply("> Komenda `" + query + "` - nie została znaleziona.", ctx.event());
            return;
        }
        StringBuilder str = new StringBuilder();
        if (!cmd.getAliases().isEmpty()) {
            for (String a : cmd.getAliases()) {
                str.append("/").append(a);
            }
        }

        final List<String> c = cmd.getHelp();
        MessageEmbed embed = new EmbedBuilder().setColor(MoonlightBot.Const.NORMAL)
            .setAuthor(author.getAsTag(), null, author.getEffectiveAvatarUrl())
            .setTitle("• " + cmd.getName() + str)
            .setDescription("> " + c.get(0) + "\n\n**Użycie:** `" + MoonlightBot.Const.PREFIX + c.get(1) + "`.")
            .build();
        Replies.commandEmbed(embed, ctx.event());
    }

    private @NotNull String cookList(final Category category) {
        StringBuilder list = new StringBuilder();
        Collection<Command> group = MoonlightBot.dispatcher().commands().values();
        if (group.size() == 0) {
            return "> Brak poleceń.";
        }

//        group.stream().map(CommandWrapper::getName).forEach(
//            (it) -> list.append("`").append(MoonlightBot.Const.PREFIX).append(it).append("`\n")
//        );
        return list.toString();
    }

    @Override
    public MessageReceivedEvent event() {
        return null;
    }

    @Override
    public String getName() {
        return "pomoc";
    }

    @Override
    public List<String> getHelp() {
        return List.of("Lista komend oraz funkcji", "pomoc <Komenda:Argument>");
    }

    @Override
    public List<String> getAliases() {
        return List.of("help", "h");
    }
}
