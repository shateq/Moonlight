package shateq.moonlight.jda.cmd;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.jda.MoonlightBot;
import shateq.moonlight.cmd.CommandWrapper;
import shateq.moonlight.cmd.CommandContext;
import shateq.moonlight.util.Replies;

import java.util.List;

public class InformationCmd implements CommandWrapper {
    @Override
    public void run(@NotNull CommandContext ctx) {
        //String data = Helpers.humanDate(MoonlightBot.startedAt, "hh:mm:ss - dd.MM.yyyy");
        double memory = (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024;

        MessageEmbed embed = new EmbedBuilder().setColor(MoonlightBot.Const.NORMAL)
            .setAuthor("Wszystko o " + ctx.getJDA().getSelfUser().getName(), null, ctx.getJDA().getSelfUser().getEffectiveAvatarUrl())
            .setDescription("> Dzień dobry! Jestem tu by dodać trochę funkcji i uczynić serwer ciekawszym. szymon9932 napisał mój kod w języku **Java**.\n" +
                "• Wpisz `" + MoonlightBot.Const.PREFIX + "pomoc`, by zobaczyć listę komend.\n" +
                "• Użyj `" + MoonlightBot.Const.PREFIX + "modules`, by otrzymać listę modułów.\n" +
                "• **Zużycie pamięci:** " + (int) memory + " MB\n\n" +
                "> 📚 **Biblioteki**\n• [JDA](https://github.com/DV8FromTheWorld/JDA) \n" +
                "• [LavaPlayer](https://github.com/sedmelluq/lavaplayer)")
            .build();
        Replies.commandEmbed(embed, ctx.event());
    }

    @Override
    public MessageReceivedEvent event() {
        return null;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public List<String> getHelp() {
        return List.of("Co to takiego?", "info");
    }

    @Override
    public List<String> getAliases() {
        return List.of("information", "informacje");
    }
}