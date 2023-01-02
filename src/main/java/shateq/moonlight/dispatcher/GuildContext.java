package shateq.moonlight.dispatcher;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.dispatcher.api.CommandContext;

/**
 * Guild-message command context
 */
public record GuildContext(MessageReceivedEvent event, String[] args) implements CommandContext<Message, MessageReceivedEvent> {
    @Override
    public @NotNull JDA jda() {
        return event.getJDA();
    }

    public @NotNull Guild guild() {
        return event.getGuild();
    }

    @Override
    public @NotNull Message source() {
        return event.getMessage();
    }

    public @NotNull User sender() {
        return event.getAuthor();
    }

    public Member member() {
        return event.getMember();
    }

    public @NotNull TextChannel channel() {
        return event.getGuildChannel().asTextChannel();
    }

    @Override
    public void reply(@NotNull String feedback) {
        source().reply(feedback).queue();
    }
}
