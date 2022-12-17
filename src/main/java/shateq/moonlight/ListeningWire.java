package shateq.moonlight;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.dispatcher.Dispatcher;

import java.util.concurrent.ThreadLocalRandom;

/**
 * General listener
 */
public final class ListeningWire extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.isWebhookMessage()) return;
        final Message msg = event.getMessage();

        if (msg.getMentions().getUsers().contains(event.getJDA().getSelfUser())) {
            char[] vowels =  new char[] {'a', 'e', 'i', 'o', 'u', 'y'};

            var pong = "P" + vowels[ThreadLocalRandom.current().nextInt(vowels.length)] + "ng!";
            event.getChannel()
                .sendMessage(pong + " Try typing `" + MoonlightBot.Const.PREFIX + "h`")
                .setMessageReference(msg).queue();
            return;
        }

        if (msg.getContentRaw().trim().startsWith(MoonlightBot.Const.PREFIX)) {
            Dispatcher.execute(event);
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
    }
}
