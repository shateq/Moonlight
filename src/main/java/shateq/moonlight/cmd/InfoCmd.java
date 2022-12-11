package shateq.moonlight.cmd;

import org.jetbrains.annotations.NotNull;
import shateq.moonlight.MoonlightBot;
import shateq.moonlight.dispatcher.Command;
import shateq.moonlight.dispatcher.GuildContext;
import shateq.moonlight.dispatcher.Order;
import shateq.moonlight.util.Replies;

@Order("info")
@Order.Explanation("Provide some information")
@Order.Rank(Command.Category.Blank)
public class InfoCmd implements Command {
    @Override
    public void execute(@NotNull GuildContext c) {
        //String data = Helpers.humanDate(MoonlightBot.startedAt, "hh:mm:ss - dd.MM.yyyy");
        double memory = (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024;
        var embed = Replies.authoredEmbed(c.jda().getSelfUser(), true)
            .setDescription("> Dzień dobry! Jestem tu by dodać trochę funkcji i uczynić serwer ciekawszym. szymon9932 napisał mój kod w języku **Java**.\n" +
                "• Wpisz `" + MoonlightBot.Const.PREFIX + "pomoc`, by zobaczyć listę komend.\n" +
                "• Użyj `" + MoonlightBot.Const.PREFIX + "modules`, by otrzymać listę modułów.\n" +
                "• **Zużycie pamięci:** " + (int) memory + " MB\n\n" +
                "> 📚 **Biblioteki**\n• [JDA](https://github.com/DV8FromTheWorld/JDA) \n" +
                "• [LavaPlayer](https://github.com/sedmelluq/lavaplayer)")
            .build();
        Replies.commandEmbed(embed, c.event());
    }
}
