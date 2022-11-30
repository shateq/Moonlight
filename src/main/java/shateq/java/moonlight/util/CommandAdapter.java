package shateq.java.moonlight.util;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface CommandAdapter {
    void run(@NotNull CommandContext ctx);

    default Guild getGuild() {
        return this.getEvent().getGuild();
    }

    MessageReceivedEvent getEvent();

    default JDA getJDA() {
        return this.getEvent().getJDA();
    }

    default TextChannel getChannel() {
        return this.getEvent().getChannel().asTextChannel();
    }

    String getName();

    List<String> getHelp();

    default List<String> getAliases() {
        return List.of();
    }
}
