package shateq.moonlight.music

import shateq.moonlight.MoonlightBot
import shateq.moonlight.dispatcher.GuildContext
import shateq.moonlight.dispatcher.api.Category
import shateq.moonlight.dispatcher.api.Order

/*
join [id]v  - Joins a voice channel that has the provided name
pause     v   - Pauses audio playback
stop      v   - Completely stops audio playback, skipping the current song.
skip     v    - Skips the current song, automatically starting the next
repeat   v    - Makes the player repeat the currently playing song
*/
@Order("play", group = Category.Music, note = "Start the music player.")
@Order.Aliases(["join"])
class Play : MusicCommand {
    override fun execute(c: GuildContext) {
        val guild = c.guild()
        val ch = guild.getVoiceChannelById(839567386935033877L)
        guild.audioManager.openAudioConnection(ch)

//        if (this.selfConnected(c)) {
//            return;
//        }
//
//        if (this.memberConnected(c)) {
//            return;
//        }
//
//        if (!this.connectionEquals(c)) {
//            Messages.Replies.quote("Kanały głosowe nie pokrywają się...", c.event()).queue();
//            return;
//        }
        MoonlightBot.jukebox().loadAndPlay(c.channel(), "https://www.youtube.com/watch?v=GHMjD0Lp5DY")
        c.source().reply("Fire!").queue()
    }
}

@Order("playlist", Category.Music, "List of tracks for the guild.")
@Order.Aliases(["tracks"])
class Playlist : MusicCommand {
    override fun execute(c: GuildContext) {
        val guild = c.guild()
        TODO()
    }
}

@Order("stop", Category.Music, "Stop the music player.")
@Order.Aliases(["leave"])
class Stop : MusicCommand {
    override fun execute(c: GuildContext) {
        val self = c.event().guild.selfMember
        val selfVoiceState = self.voiceState

        if (selfConnected(c)) {
            return
        }

        val member = c.event().member
        val memberVoiceState = member!!.voiceState

        throw Exception("He he he ha")
    }
}

@Order("skip", Category.Music, "Skip current or to the track.")
class Skip : MusicCommand {
    override fun execute(c: GuildContext) {
        TODO("Not yet implemented")
    }
}

@Order("pause", Category.Music, "Pause the track.")
class Pause : MusicCommand {
    override fun execute(c: GuildContext) {
        TODO()
    }
}

@Order("repeat", Category.Music, "Repeat current track or start da capo!")
class Repeat : MusicCommand {
    override fun execute(c: GuildContext) {
        TODO("W trakcie")
    }
}

@Order("reset", Category.Music, "Destroy current playlist, clear etc.")
@Order.Aliases(["destroy"])
class Destroy : MusicCommand {
    override fun execute(c: GuildContext) {
        TODO("That one is a banger")
    }
}
