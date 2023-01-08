package shateq.moonlight.dispatcher;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.dispatcher.api.CommandContext;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public record SlashContext(SlashCommandInteractionEvent event) implements CommandContext<SlashCommandInteraction, SlashCommandInteractionEvent> {
    @Override
    public @NotNull JDA jda() {
        return event.getJDA();
    }

    @Override
    public @NotNull Guild guild() {
        return Objects.requireNonNull(event.getGuild());
    }

    @Override
    public @NotNull SlashCommandInteraction source() {
        return event.getInteraction();
    }

    @Override
    public @NotNull User sender() {
        return event.getUser();
    }

    @Override
    public void reply(@NotNull String feedback) {
        source().reply(feedback).queue();
    }

    @Override
    public void replyEmbeds(MessageEmbed @NotNull ... embeds) {
        source().replyEmbeds(Arrays.asList(embeds)).queue();
    }

    public @NotNull TextChannel channel() {
        return event.getChannel().asTextChannel();
    }

    public @NotNull List<OptionMapping> options() {
        return event.getOptions();
    }
}
