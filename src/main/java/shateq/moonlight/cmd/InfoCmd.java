package shateq.moonlight.cmd;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.MoonlightBot;
import shateq.moonlight.dispatcher.Dispatcher;
import shateq.moonlight.dispatcher.GuildContext;
import shateq.moonlight.dispatcher.SlashContext;
import shateq.moonlight.dispatcher.api.Command;
import shateq.moonlight.dispatcher.api.Order;
import shateq.moonlight.util.Orbit;

import java.util.Date;

@Order(value = "info", note = "A piece of information.")
@Order.Aliases("explain")
public class InfoCmd implements Command {
    public InfoCmd() {
        Dispatcher.upsertCommandData(Commands.slash("info", "Some information."));
    }

    public MessageEmbed information() {
        String data = Orbit.simpleDateFormat(new Date(), "hh:mm:ss - dd.MM.yyyy");
        double memory = (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024;

        return Orbit.colourEmbed(true)
            .setAuthor(MoonlightBot.jda().getSelfUser().getAsTag())
            .setDescription("Dzień dobry! Jestem tu by zaimplementować autorskie wymysły. " +
                "szymon9932 napisał mój kod w języku **Java**. Link: [GitHub](" + MoonlightBot.Const.GITHUB_URL + ")\n\n" +
                "• Wpisz `" + MoonlightBot.Const.PREFIX + "pomoc`, by zobaczyć listę komend.\n" +
                "• Użyj `" + MoonlightBot.Const.PREFIX + "modules`, by otrzymać listę modułów.\n\n" +
                "Zużycie pamięci: " + (int) memory + " MB"
            )
            .setFooter(data).build();
    }

    @Override
    public void execute(@NotNull GuildContext c) {
        c.source().replyEmbeds(information()).queue();
    }

    @Override
    public void slash(@NotNull SlashContext c) {
        c.source().replyEmbeds(information()).queue();
    }
}
