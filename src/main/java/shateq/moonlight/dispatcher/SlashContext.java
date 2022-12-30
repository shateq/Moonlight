package shateq.moonlight.dispatcher;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import org.jetbrains.annotations.NotNull;

public record SlashContext(SlashCommandInteractionEvent event) {
    public @NotNull User sender() {
        return event.getUser();
    }

    public @NotNull SlashCommandInteraction interaction() {
        return event.getInteraction();
    }

    public @NotNull JDA jda() {
        return event.getJDA();
    }
}
