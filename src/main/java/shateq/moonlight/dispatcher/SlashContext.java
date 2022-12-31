package shateq.moonlight.dispatcher;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public record SlashContext(SlashCommandInteractionEvent event) {
    public @NotNull Guild guild() {
        return Objects.requireNonNull(event.getGuild());
    }

    public @NotNull User sender() {
        return event.getUser();
    }

    public @NotNull List<OptionMapping> options() {
        return event.getOptions();
    }

    public @NotNull SlashCommandInteraction interaction() {
        return event.getInteraction();
    }

    public @NotNull JDA jda() {
        return event.getJDA();
    }
}
