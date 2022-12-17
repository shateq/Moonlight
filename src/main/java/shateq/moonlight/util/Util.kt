package shateq.moonlight.util

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction
import org.jetbrains.annotations.Contract
import shateq.moonlight.MoonlightBot

/**
 * Common methods across the project
 */
class Util {
    companion object Replies {
        fun authoredEmbed(author: User, normal: Boolean): EmbedBuilder {
            return EmbedBuilder()
                .setAuthor(author.asTag, null, author.effectiveAvatarUrl)
                .setColor(if (normal) MoonlightBot.Const.NORMAL else MoonlightBot.Const.BAD)
        }

        fun coloredEmbed(normal: Boolean): EmbedBuilder {
            return EmbedBuilder()
                .setColor(if (normal) MoonlightBot.Const.NORMAL else MoonlightBot.Const.BAD)
        }

        @Contract("_, _ -> new")
        fun quote(message: CharSequence, e: MessageReceivedEvent): MessageCreateAction {
            return e.channel.sendMessage("> $message").setMessageReference(e.message)
        }

        @Contract("_, _ -> new")
        fun simply(msg: CharSequence?, e: MessageReceivedEvent): MessageCreateAction {
            return e.channel.sendMessage(msg!!).setMessageReference(e.message)
        }

        @Contract("_, _ -> new")
        fun embed(msg: MessageEmbed?, e: MessageReceivedEvent): MessageCreateAction {
            return e.channel.sendMessageEmbeds(msg!!).setMessageReference(e.message)
        }
    }
}
