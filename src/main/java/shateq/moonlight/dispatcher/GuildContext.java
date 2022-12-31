package shateq.moonlight.dispatcher;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Guild-message command context
 */
public record GuildContext(MessageReceivedEvent event, String[] args) {
    public @NotNull Guild guild() {
        return event.getGuild();
    }

    public @NotNull User sender() {
        return event.getAuthor();
    }

    public Member member() {
        return event.getMember();
    }

    public @NotNull Message message() {
        return event.getMessage();
    }

    public @NotNull TextChannel channel() {
        return event.getGuildChannel().asTextChannel();
    }

    public @NotNull JDA jda() {
        return event.getJDA();
    }
}
