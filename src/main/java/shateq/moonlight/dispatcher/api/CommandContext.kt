package shateq.moonlight.dispatcher.api

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.Event

/**
 * Essential context mapping
 */
interface CommandContext<S, E : Event> {
    /**
     * Type of the event
     * e.g. MessageReceived or SlashCommandInteraction
     */
    fun event(): E

    /**
     * JDA instance
     */
    fun jda(): JDA = event().jda
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
}
