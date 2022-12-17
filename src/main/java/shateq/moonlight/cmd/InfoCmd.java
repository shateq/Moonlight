package shateq.moonlight.cmd;

import org.jetbrains.annotations.NotNull;
import shateq.moonlight.MoonlightBot;
import shateq.moonlight.dispatcher.Command;
import shateq.moonlight.dispatcher.GuildContext;
import shateq.moonlight.dispatcher.Order;
import shateq.moonlight.util.Outer;
import shateq.moonlight.util.Util;

import java.util.Date;

@Order("info")
@Order.Aliases("explain")
@Order.Explanation("Provide some information")
@Order.Rank()
public class InfoCmd implements Command {
    @Override
    public void execute(@NotNull GuildContext c) {
        String data = Outer.simpleDateFormat(new Date(), "hh:mm:ss - dd.MM.yyyy");

        double memory = (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024;
        var embed = Util.Replies.authoredEmbed(c.jda().getSelfUser(), true)
            .setDescription("Dzień dobry! Jestem tu by zaimplementować autorskie wymysły. " +
                "szymon9932 napisał mój kod w języku **Java**. Link: [GitHub](" + MoonlightBot.Const.GITHUB_URL + ")\n\n" +
                "• Wpisz `" + MoonlightBot.Const.PREFIX + "pomoc`, by zobaczyć listę komend.\n" +
                "• Użyj `" + MoonlightBot.Const.PREFIX + "modules`, by otrzymać listę modułów.\n\n" +
                "Zużycie pamięci: " + (int) memory + " MB"
            )
            .setFooter(data)
            .build();
        Util.Replies.embed(embed, c.event()).queue();
    }
}
