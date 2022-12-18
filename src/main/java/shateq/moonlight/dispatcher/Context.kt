package shateq.moonlight.dispatcher

import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

data class Context(
    @get:JvmName("args") val args: List<String>,
    @get:JvmName("event") val event: MessageReceivedEvent,
    @get:JvmName("sender") val sender: User
) {
    fun message() = event.message

    fun jda() = event.jda
}
