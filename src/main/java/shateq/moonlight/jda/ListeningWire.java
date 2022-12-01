package shateq.moonlight.jda;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public final class ListeningWire extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent e) {
        if (e.getAuthor().isBot() || e.isWebhookMessage()) return;
        final Message msg = e.getMessage();

        if (msg.getMentions().getMentions(Message.MentionType.USER).contains(MoonlightBot.it().jda().getSelfUser())) {
            enum Vowel {a, e, i, o, u, y}
            e.getChannel()
                .sendMessage("P" + Vowel.values()[ThreadLocalRandom.current().nextInt(Vowel.values().length)] + "ng! 🏓")
                .setMessageReference(msg).queue();
        }

        if (msg.getContentRaw().startsWith(MoonlightBot.Const.PREFIX)) {
            OrderGround.genericCommand(e);
        }
    }

    @Override
    public void onReady(@NotNull ReadyEvent e) {
        MoonlightBot.
            log.info("Logged in as {}!", e.getJDA().getSelfUser().getAsTag());
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {}

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {}
}
