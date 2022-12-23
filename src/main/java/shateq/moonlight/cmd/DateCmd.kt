package shateq.moonlight.cmd

import dev.minn.jda.ktx.messages.send
import shateq.moonlight.dispatcher.GuildContext
import shateq.moonlight.dispatcher.api.Command
import shateq.moonlight.dispatcher.api.Order

@Order("date")
@Order.Aliases(["time", "unix"])
@Order.Explanation("Timestamp utilities")
class DateCmd : Command {
    override fun execute(c: GuildContext) {
        c.event.channel.send("yo").queue()
        TODO("Not yet implemented")
    }
}
