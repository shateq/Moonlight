package shateq.moonlight.music

import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel
import shateq.moonlight.MoonlightBot
import shateq.moonlight.dispatcher.GuildContext
import shateq.moonlight.dispatcher.api.Command

interface MusicCommand : Command {
    fun connectionEquals(c: GuildContext): Boolean {
        val member = c.member().voiceState
        val self = c.guild().selfMember.voiceState
        return if (member == null || self == null) false else member.channel!!.id == self.channel!!.id
    }

    fun selfConnected(c: GuildContext): Boolean {
        val self = c.guild().selfMember.voiceState
        if (self != null && !self.inAudioChannel()) {
            c.source().reply("Muszę być na kanale głosowym, by to zadziałało!").queue()
            return false
        }
        return true
    }

    fun memberConnected(c: GuildContext): Boolean {
        val member = c.member().voiceState
        if (member != null && !member.inAudioChannel()) {
            c.source().reply("Nie znam kanału do którego jesteś połączony(-a).").queue()
            return false
        }
        return true
    }

    fun voiceChannelEmpty(chan: VoiceChannel): Boolean {
        return chan.members.isEmpty()
    }

    fun guildContainer(guild: Guild): GuildMusicContainer {
        return MoonlightBot.jukebox().getGuildContainer(guild)
    }
}
