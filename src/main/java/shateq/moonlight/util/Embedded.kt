package shateq.moonlight.util

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction
import org.jetbrains.annotations.Contract
import org.slf4j.helpers.CheckReturnValue
import shateq.moonlight.MoonlightBot

/**
 * Common feedback, around MessageEmbed
 */
class Embedded {
    companion object A {
        fun authoredEmbed(author: User, normal: Boolean): EmbedBuilder =
            EmbedBuilder()
                .dye(normal)
                .setAuthor(author.asTag, null, author.effectiveAvatarUrl)

        private fun EmbedBuilder.dye(normal: Boolean): EmbedBuilder =
            this.setColor(if (normal) MoonlightBot.Const.NORMAL else MoonlightBot.Const.BAD)

        fun coloredEmbed(normal: Boolean): EmbedBuilder =
            EmbedBuilder().dye(normal)

        @Contract("_, _ -> new")
        @CheckReturnValue
        fun embed(outputEmbed: MessageEmbed, e: MessageReceivedEvent): MessageCreateAction =
            e.channel.sendMessageEmbeds(outputEmbed).setMessageReference(e.message)

        fun skull(): String = "💀"
    }
}
