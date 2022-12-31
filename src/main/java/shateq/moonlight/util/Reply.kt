package shateq.moonlight.util

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction
import org.jetbrains.annotations.Contract
import org.slf4j.helpers.CheckReturnValue
import shateq.moonlight.MoonlightBot

/**
 * Common methods across the project
 */
class Reply {
    // TODO REFACTOR
    companion object A {
        fun authoredEmbed(author: User, normal: Boolean): EmbedBuilder =
            EmbedBuilder()
                .dye(normal)
                .setAuthor(author.asTag, null, author.effectiveAvatarUrl)

        private fun EmbedBuilder.dye(normal: Boolean): EmbedBuilder =
            this.setColor(if (normal) MoonlightBot.Const.NORMAL else MoonlightBot.Const.BAD)

        fun coloredEmbed(normal: Boolean): EmbedBuilder =
            EmbedBuilder().dye(normal)

        fun skull(event: MessageReceivedEvent) = just("💀", event).queue()

        @Contract("_, _ -> new")
        @CheckReturnValue
        fun reference(output: CharSequence, e: MessageReceivedEvent): MessageCreateAction =
            e.channel.sendMessage(output).setMessageReference(e.message)

        @Contract("_, _ -> new")
        @CheckReturnValue
        fun quote(output: CharSequence, e: MessageReceivedEvent): MessageCreateAction =
            e.channel.sendMessage("> $output").setMessageReference(e.message)

        @Contract("_, _ -> new")
        @CheckReturnValue
        fun quote(output: CharSequence, ch: TextChannel): MessageCreateAction =
            ch.sendMessage("> $output")

        @Contract("_, _ -> new")
        @CheckReturnValue
        fun just(message: CharSequence, e: MessageReceivedEvent): MessageCreateAction =
            e.channel.sendMessage(message)

        @Contract("_, _ -> new")
        @CheckReturnValue
        fun embed(outputEmbed: MessageEmbed, e: MessageReceivedEvent): MessageCreateAction =
            e.channel.sendMessageEmbeds(outputEmbed).setMessageReference(e.message)
    }
}
