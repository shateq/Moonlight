package shateq.moonlight.cmd

import dev.minn.jda.ktx.messages.send
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Guild
import shateq.moonlight.dispatcher.GuildContext
import shateq.moonlight.dispatcher.api.Command
import shateq.moonlight.dispatcher.api.Order

@Order("hej", note = "List powitalny.")
@Order.Aliases(["hei", "moi"])
class HejCmd : Command {
    override fun execute(c: GuildContext) {
        c.event.channel.send(hello(c.guild())).queue()
    }

    fun hello(guild: Guild): String {
        val perms = guild.selfMember.permissions.contains(Permission.ADMINISTRATOR)
        return "> Jestli to ${guild.name}?!\nSerdeczne życzenia zdrowia i pomyślności.\n" +
            "**Spełnianie wymagań?** `$perms`. (Aplikacja wymaga uprawnień ADMINISTRATORA do poprawnego działania.)"
    }
}
