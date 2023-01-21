package shateq.moonlight.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.MoonlightBot;
import shateq.moonlight.util.Orbit;

import java.util.HashMap;
import java.util.Map;

/**
 * Manager due to every guild having their own GuildContainer.
 */
public class JukeboxManager {
    private final Map<Long, GuildMusicContainer> guildContainers;
    private final AudioPlayerManager playerManager;
    // TODO ScheduledExecutor service for closing inactive-s

    public JukeboxManager() {
        MoonlightBot.GUILDS.info("Jukebox consumed a coin...");
        guildContainers = new HashMap<>();
        playerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    public synchronized GuildMusicContainer getGuildContainer(@NotNull Guild guild) {
        return guildContainers.computeIfAbsent(guild.getIdLong(), (id) -> {
            GuildMusicContainer musicContainer = new GuildMusicContainer(playerManager);

            guild.getAudioManager().setSendingHandler(musicContainer.getSendHandler());
            return musicContainer;
        });
    }

    public void loadAndPlay(@NotNull Guild guild, @NotNull TextChannel channel, String trackUrl) {
        var musicContainer = guildContainers.get(guild.getIdLong());
        playerManager.loadItemOrdered(musicContainer, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                musicContainer.scheduler.queue(track);

                channel.sendMessage("\uD83D\uDCFB Dodano do kolejki...\n*" + track.getInfo().title + "* ~ " + track.getInfo().author +
                    " `" + Orbit.toTimestamp(track.getInfo().length) + "`").queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null)
                    firstTrack = playlist.getTracks().get(0);

                channel.sendMessage("Dodawanie do kolejki " + firstTrack.getInfo().title + " (pierwszy utwór na liście " + playlist.getName() + ")").queue();

                play(guild, firstTrack);
            }

            @Override
            public void noMatches() {
                channel.sendMessage("**Nie znaleziono nic dla:** " + trackUrl).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("Wyjątek podczas próby muzycznej: " + exception.getMessage()).queue();
            }
        });
    }

    public void play(@NotNull Guild guild, AudioTrack track) {
        //connectToFirstVoiceChannel(guild.getAudioManager());
        guildContainers.get(guild.getIdLong()).scheduler.queue(track);
    }

    public void skipTrack(@NotNull TextChannel chan) {
        guildContainers.get(chan.getGuild().getIdLong()).scheduler.nextTrack();
        chan.sendMessage("Aktualnie odtwarzany utwór został pominięty.").queue();
    }

    public void togglePause(@NotNull Guild guild, @NotNull TextChannel chan) {
        var player = guildContainers.get(guild.getIdLong()).audioPlayer;
        if (player.getPlayingTrack() == null) {
            chan.sendMessage("Nie można zatrzymać ani ponowić utworu, ponieważ żaden utwór nie jest załadowany na playliście.").queue();
            return;
        }
        player.setPaused(!player.isPaused());
        if (player.isPaused())
            chan.sendMessage("Wsztrzymano odgrywanie utworu.").queue();
        else
            chan.sendMessage("Ponowiono odgrywanie utworu.").queue();
    }

    public void closeConnection(@NotNull TextChannel chan) {
        var g = chan.getGuild();
        g.getAudioManager().setSendingHandler(null);
        g.getAudioManager().closeAudioConnection();

        chan.sendMessage("Zamknięto połączenie, jednak playlista pozostaje załadowana dla tego serwera.").queue();
    }
}
