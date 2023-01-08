package shateq.moonlight

import dev.minn.jda.ktx.messages.send
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.events.guild.GuildReadyEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import reactor.core.publisher.Mono
import shateq.moonlight.cmd.HejCmd
import shateq.moonlight.dispatcher.Dispatcher
import shateq.moonlight.mod.api.Place
import java.time.Duration
import java.util.concurrent.ExecutorService
import java.util.concurrent.ThreadLocalRandom

class ListeningWire(private val threadPool: ExecutorService) : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot || event.isWebhookMessage) return
        val message = event.message
        MoonlightBot.LOG.debug(message.contentRaw)

        if (message.mentions.users.contains(event.jda.selfUser)) {
            val vowels = charArrayOf('a', 'e', 'i', 'o', 'u', 'y')
            val pong = "P" + vowels[ThreadLocalRandom.current().nextInt(vowels.size)] + "ng!"
            event.message.reply("$pong Try typing `${MoonlightBot.Constant.PREFIX} h`").setMessageReference(event.message).queue()
            return
        }

        if (event.isFromGuild) {
            threadPool.execute {
                Dispatcher.processText(event)
            }
        }
    }

    override fun onGuildReady(event: GuildReadyEvent) {
        threadPool.execute {
            MoonlightBot.GUILDS.info("{} is ready! Preparing its modules.", Place(event.guild))
            // some ModuleChute
        }
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) = threadPool.execute { Dispatcher.processSlash(event) }

    override fun onGuildJoin(event: GuildJoinEvent) {
        val guild = event.guild
        val mono = Mono.create<Unit> {
            sayHelloOnJoin(guild)
        }
        mono.delaySubscription(Duration.ofSeconds(10))
            .subscribe()
    }

    private fun sayHelloOnJoin(guild: Guild) {
        var channel: TextChannel? = guild.systemChannel ?: guild.defaultChannel?.asTextChannel()
        if (channel == null || !channel.canTalk()) {
            guild.textChannels.forEach { tc ->
                if (tc.canTalk()) {
                    channel = tc
                    return@forEach
                }
            }
        }
        channel?.send(
            HejCmd().hello(guild)
        )?.queue()
    }
}
