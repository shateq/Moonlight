package shateq.moonlight.music

import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.GuildVoiceState
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel
import shateq.moonlight.MoonlightBot
import shateq.moonlight.dispatcher.GuildContext
import shateq.moonlight.dispatcher.api.Command
import shateq.moonlight.dispatcher.api.CommandContext

interface MusicCommand : Command {
    fun selfVoiceState(c: CommandContext<Any, Any>): GuildVoiceState? {
        return c.guild().selfMember.voiceState
    }

    fun senderVoiceState(c: CommandContext<Any, Any>): GuildVoiceState? {
        return c.member().voiceState
    }

    fun sameChannel(c: CommandContext<Any, Any>): Boolean {
        return selfVoiceState(c)?.channel?.id == senderVoiceState(c)?.channel?.id
    }

    fun memberConnected(c: GuildContext): Boolean {
        val member = c.member().voiceState
        if (member != null && !member.inAudioChannel()) {
            c.source().reply("Nie znam kanału do którego jesteś połączony(-a).").queue()
            return false
        }
        return true
    }

    fun openTunnel(chan: VoiceChannel) {
        // TODO checks
        chan.guild.audioManager.openAudioConnection(chan)
    }

    fun isEmpty(chan: VoiceChannel): Boolean {
        return chan.members.isEmpty()
    }

    fun guildContainer(guild: Guild): GuildMusicContainer {
        return MoonlightBot.jukebox().getGuildContainer(guild)
    }
}
