package shateq.moonlight.jda;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shateq.moonlight.dispatcher.Dispatcher;
import shateq.moonlight.util.Alphabet;

import java.util.concurrent.ThreadLocalRandom;

public final class ListeningWire extends ListenerAdapter {
    private static final Logger LOG = LoggerFactory.getLogger("Listeners");

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.isWebhookMessage()) return;
        final Message msg = event.getMessage();

        if (msg.getMentions().getUsers().contains(event.getJDA().getSelfUser())) {
            var pong = "P" + Alphabet.Vowel.values()[ThreadLocalRandom.current().nextInt(Alphabet.Vowel.values().length)] + "ng!";
            event.getChannel()
                .sendMessage(pong + " Try typing `"+ MoonlightBot.Const.PREFIX +"h`")
                .setMessageReference(msg).queue();
            return;
        }

        if (msg.getContentRaw().trim().startsWith(MoonlightBot.Const.PREFIX)) {
            Dispatcher.execute(event);
        }
    }

    @Override
    public void onReady(@NotNull ReadyEvent e) {
        LOG.info("Logged in as {}!", e.getJDA().getSelfUser().getAsTag());
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
    }
}
