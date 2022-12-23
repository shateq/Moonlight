package shateq.moonlight

import net.dv8tion.jda.api.events.guild.GuildLeaveEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import shateq.moonlight.dispatcher.Dispatcher
import shateq.moonlight.util.Messages
import java.util.concurrent.ThreadLocalRandom

class ListeningWire : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot || event.isWebhookMessage) return

        val message = event.message

        if (message.mentions.users.contains(event.jda.selfUser)) {
            val vowels = charArrayOf('a', 'e', 'i', 'o', 'u', 'y')
            val pong = "P" + vowels[ThreadLocalRandom.current().nextInt(vowels.size)] + "ng!"
            Messages.reference("$pong Try typing `${MoonlightBot.Const.PREFIX} h`", event).queue()
            return
        }

        if (event.isFromGuild) {
            Dispatcher.process(event)
        }
    }

    override fun onGuildLeave(event: GuildLeaveEvent) {
        // db action
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        // slash commander
    }
}
