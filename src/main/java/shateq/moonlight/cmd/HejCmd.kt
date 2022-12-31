package shateq.moonlight.cmd

import dev.minn.jda.ktx.messages.send
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Guild
import shateq.moonlight.dispatcher.GuildContext
import shateq.moonlight.dispatcher.api.Command
import shateq.moonlight.dispatcher.api.Order

@Order("hej", note = "List powitalny.")
@Order.Aliases(["hei", "moi"])
@Order.Hidden //usage
class HejCmd : Command {
    override fun execute(c: GuildContext) {
        c.event.channel.send(hello(c.guild())).queue()
    }

    fun hello(guild: Guild): String {
        val perms = guild.selfMember.permissions.contains(Permission.ADMINISTRATOR)
        val lang = guild.locale.nativeName
        return """
            > Jestli to ${guild.name}?! Życzenia zdrowia i pomyślności.
            Język tego serwera to być może $lang.
            
            Pewnie się dogadamy, `->modules`.
            **Czy aplikacja będzie poprawnie działać?** `$perms` (Aplikacja wymaga uprawnień ADMINISTRATORA)"
        """.trimIndent()
    }
}
