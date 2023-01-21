package shateq.moonlight.music

import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.interactions.commands.build.Commands
import shateq.moonlight.MoonlightBot
import shateq.moonlight.dispatcher.Dispatcher
import shateq.moonlight.dispatcher.GuildContext
import shateq.moonlight.dispatcher.SlashContext
import shateq.moonlight.dispatcher.api.Category
import shateq.moonlight.dispatcher.api.ContextualException
import shateq.moonlight.dispatcher.api.Order
import shateq.moonlight.util.Orbit

@Order("play", group = Category.Music, note = "Start the music player.")
@Order.Aliases(["join", "p"])
class Play : MusicCommand {
    override fun execute(c: GuildContext) {
        val container = guildContainer(c.guild())
        val guild = c.guild()

        if (c.args.isEmpty()) {
            if (container.audioPlayer.isPaused) {
                container.audioPlayer.isPaused = !container.audioPlayer.isPaused
                c.channel().sendMessage("Odtwarzanie zostało wznowione.").queue()
            } else if (container.scheduler.queue.isEmpty()) {
                c.source().reply("Kolejka jest pusta! Musisz coś do niej dodać, by odtwarzanie znalazło początek.").queue()
            }
            return
        }

        val ch = guild.getVoiceChannelById(839567386935033877L)
        guild.audioManager.openAudioConnection(ch)

        MoonlightBot.jukebox().loadAndPlay(
            c.guild(), c.channel(), c.args[0]//.joinToString(" ")
        )
        c.source().reply("Odbiór, dodawanie do kolejki!").queue()
    }
}


@Order("playlist", Category.Music, "Playlist of the guild.")
@Order.Aliases(["tracks", "list"])
class Playlist : MusicCommand {

    override fun execute(c: GuildContext) {
        val scheduler = guildContainer(c.guild()).scheduler
        val queue = scheduler.queue

        synchronized(queue) {
            if (queue.isEmpty())
                c.source().reply("Kolejka jest aktualnie pusta.").queue()
            else {
                var l: Long = 0
                val list = StringBuilder()
                for ((i, t) in queue.withIndex()) {
                    l += t.duration
                    if (i < 13)
                        list.append("$i. ").append("*${t.info.title}* ")
                            .append("`${Orbit.toTimestamp(t.duration)}`").append("\n")
                }

                val embed = Orbit.colourEmbed(MoonlightBot.Constant.MUSIC)
                    .setTitle("Kolejka - ${queue.size} utworów").setFooter("Całkowity czas kolejki - ${Orbit.toTimestamp(l)}")
                    .setDescription(list.toString())
                c.source().replyEmbeds(embed.build()).queue()
            }
        }
    }
}

@Order("nowplaying", Category.Music, "The currently playing track.")
@Order.Aliases(["now"])
class NowPlaying : MusicCommand {
    init {
        Dispatcher.upsertCommandData(Commands.slash("now", "Check the currently playing track.").setGuildOnly(true))
    }

    override fun execute(c: GuildContext) {
        c.source().reply(
            nowPlaying(c.guild())
        ).queue()
    }

    override fun slash(c: SlashContext) {
        c.source().reply(
            nowPlaying(c.guild())
        ).queue()
    }

    private fun nowPlaying(g: Guild): String {
        val player = guildContainer(g).audioPlayer
        val playing = player.playingTrack ?: throw ContextualException("Żaden utwór nie jest aktualnie odtwarzany.")

        val position = Orbit.toTimestamp(playing.position)
        val duration = Orbit.toTimestamp(playing.duration)

        return "__Odtwarzanie:__ *${playing.info.title}* ~ ${playing.info.author} `$position/$duration`"
    }
}

// TODO some inconsistency
@Order("leave", Category.Music, "Leave the current channel.")
@Order.Aliases(["out"])
class Leave : MusicCommand {
    init {
        Dispatcher.upsertCommandData(Commands.slash("leave", "Close audio connection."))
    }

    override fun execute(c: GuildContext) {
        MoonlightBot.jukebox().closeConnection(c.channel())
    }

    override fun slash(c: SlashContext) {
        MoonlightBot.jukebox().closeConnection(c.channel())
    }
}

@Order("stop", Category.Music, "Destroy current playlist, clear etc.")
@Order.Aliases(["destroy"])
class Destroy : MusicCommand {
    override fun execute(c: GuildContext) {
        val container = guildContainer(c.guild())

        container.scheduler.queue.clear()
        container.audioPlayer.stopTrack()
        container.audioPlayer.isPaused = false

        c.source().reply("Odtwarzacz kompletnie zatrzymano, a kolejka została wyczyszczona.").queue()
    }
}

@Order("skip", Category.Music, "Skip current or to the track.")
class Skip : MusicCommand {
    override fun execute(c: GuildContext) {
        MoonlightBot.jukebox().skipTrack(c.channel())
    }
}

@Order("pause", Category.Music, "Pause the track.")
@Order.Aliases(["resume"])
class Pause : MusicCommand {
    override fun execute(c: GuildContext) {
        MoonlightBot.jukebox().togglePause(c.guild(), c.channel())
    }
}

@Order("repeat", Category.Music, "The current track da capo!")
class Repeat : MusicCommand {
    override fun execute(c: GuildContext) {
        val scheduler = guildContainer(c.guild()).scheduler

        scheduler.isRepeating = !scheduler.isRepeating
        if (scheduler.isRepeating)
            c.source().reply("Odwarzacz został ustawiony na powtarzanie **1** utworu.").queue()
        else
            c.source().reply("Odwarzacz nie powtarza już utworu.").queue()
    }
}

@Order("shuffle", Category.Music, "Shuffle current queue.")
class Shuffle : MusicCommand {
    override fun execute(c: GuildContext) {
        val scheduler = guildContainer(c.guild()).scheduler

        if (scheduler.queue.isEmpty() || scheduler.queue.count() <= 1) {
            c.source().reply("Właściwie to nie ma nic do pomieszania w tej kolejce.").queue()
        }

        scheduler.shuffle()
        c.source().reply("Kolejka została pomieszana").queue()
    }
}
