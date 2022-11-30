package shateq.java.moonlight.cmd;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import shateq.java.moonlight.CommandHandler;
import shateq.java.moonlight.Main;
import shateq.java.moonlight.Moonlight;
import shateq.java.moonlight.util.CommandAdapter;
import shateq.java.moonlight.util.CommandContext;
import shateq.java.moonlight.util.Helpers;

import java.util.List;

public class InformationCmd implements CommandAdapter {
    @Override
    public void run(@NotNull CommandContext ctx) {
        final String p = Main.get("prefix");
        String data = Helpers.humanDate(Main.startedAt, "hh:mm:ss - dd.MM.yyyy");
        double memory = (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024;

        MessageEmbed embed = new EmbedBuilder().setColor(Moonlight.NORMAL).setAuthor("Wszystko o "+ ctx.getJDA().getSelfUser().getName(), null, ctx.getJDA().getSelfUser().getEffectiveAvatarUrl()).setDescription("> Dzień dobry! Jestem tu by dodać trochę funkcji i uczynić serwer ciekawszym. szymon9932 napisał mój kod w języku **Java**.\n• Wpisz `"+p+"pomoc`, by zobaczyć listę komend.\n• Użyj `"+p+"modules`, by otrzymać listę modułów.\n\n• **Zużycie pamięci:** "+(int) memory+" MB\n• **Start o czasie:** "+ data+"\n\n> 📚 **Biblioteki**\n• [JDA](https://github.com/DV8FromTheWorld/JDA) \n• [LavaPlayer](https://github.com/sedmelluq/lavaplayer)").build();
        CommandHandler.commandEmbed(embed, ctx.getEvent());
    }

    @Override
    public GuildMessageReceivedEvent getEvent() {
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