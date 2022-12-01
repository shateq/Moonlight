package shateq.java.moonlight.cmd;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import shateq.java.moonlight.CommandHandler;
import shateq.java.moonlight.MoonlightBot;
import shateq.java.moonlight.util.Category;
import shateq.java.moonlight.util.CommandAdapter;
import shateq.java.moonlight.util.CommandContext;

import java.util.Collection;
import java.util.List;

public class HelpCmd implements CommandAdapter {

    @Override
    public void run(@NotNull CommandContext ctx) {
        final List<String> args = ctx.getArgs();
        final User author = ctx.getEvent().getAuthor();

        if (args.isEmpty()) {
            final Collection<CommandAdapter> commands = CommandHandler.showCommands();
            final String blank = cookList(Category.Blank);
            final String games = cookList(Category.Games);
            final String music = cookList(Category.Music);

            EmbedBuilder list = new EmbedBuilder().setColor(MoonlightBot.Const.NORMAL).setTitle("• Pomoc (" + commands.size() + " komend)").setAuthor(author.getAsTag(), null, author.getEffectiveAvatarUrl())
                .setDescription(blank).addField(Category.Games.title, games, false).addField(Category.Music.title, music, false);

            //if(Aki) {
            //    list.addField(Category.Akinator.title, cookList(Category.Akinator), false);
            //}

            CommandHandler.commandEmbed(list.build(), ctx.getEvent());
            return;
        }

        final String query = args.get(0);
        CommandAdapter cmd = CommandHandler.getCommand(query);
        if (cmd == null) {
            CommandHandler.commandReply("> Komenda `" + query + "` - nie została znaleziona.", ctx.getEvent());
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
        CommandHandler.commandEmbed(embed, ctx.getEvent());
    }

    private @NotNull String cookList(final Category category) {
        StringBuilder list = new StringBuilder();
        Collection<CommandAdapter> group = CommandHandler.showCommands(category);
        assert group != null;
        if (group.size() == 0) {
            return "> Brak poleceń.";
        }

        group.stream().map(CommandAdapter::getName).forEach(
            (it) -> list.append("`").append(MoonlightBot.Const.PREFIX).append(it).append("`\n")
        );
        return list.toString();
    }

    @Override
    public MessageReceivedEvent getEvent() {
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
