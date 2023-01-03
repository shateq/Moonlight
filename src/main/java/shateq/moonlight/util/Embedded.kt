package shateq.moonlight.util

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.User
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
    }
}
