package shateq.moonlight.cmd;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface CommandWrapper {
    void run(@NotNull CommandContext ctx);

    default Guild getGuild() {
        return this.event().getGuild();
    }

    MessageReceivedEvent event();

    default JDA getJDA() {
        return this.event().getJDA();
    }

    default TextChannel getChannel() {
        return this.event().getChannel().asTextChannel();
    }

    String getName();

    List<String> getHelp();

    default List<String> getAliases() {
        return List.of();
    }
}
