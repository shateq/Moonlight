package shateq.moonlight.dispatcher.api

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.User

/**
 * Essential context mapping
 */
interface CommandContext<S, E> {
    /**
     * Type of the event
     * e.g. MessageReceived or SlashCommandInteraction
     */
    fun event(): E

    /**
     * JDA instance
     */
    fun jda(): JDA

    /**
     * A place for the event
     */
    fun guild(): Guild

    /**
     * Reason for this event
     * e.g. SlashCommandInteraction or Message
     */
    fun source(): S

    /**
     * Invoker, your problem
     */
    fun sender(): User

    /**
     * Queue a MessageCreateAction
     */
    fun reply(feedback: String) {}

    /**
     * Queue a MessageCreateAction featuring embed
     */
    fun replyEmbeds(vararg embeds: MessageEmbed) {}
}
