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

import java.util.HashMap;
import java.util.Map;

public class JukeboxManager {
    private final Map<Long, GuildMusicContainer> guildContainers;
    private final AudioPlayerManager playerManager;

    public JukeboxManager() {
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

    public void loadAndPlay(@NotNull TextChannel channel, String trackUrl) {
        GuildMusicContainer musicContainer = getGuildContainer(channel.getGuild());

        playerManager.loadItemOrdered(musicContainer, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                musicContainer.scheduler.queue(track);

                channel.sendMessage("> \uD83D\uDCFB **Dodano do kolejki...**\n`" + track.getInfo().title + "` - `" + track.getInfo().author + "` **(" + track.getInfo().length + ")**").queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }

                channel.sendMessage("Dodawanie do kolejki " + firstTrack.getInfo().title + " (pierwszy utwór na liście " + playlist.getName() + ")").queue();

                play(channel.getGuild(), musicContainer, firstTrack);
            }

            @Override
            public void noMatches() {
                channel.sendMessage("Nie znaleziono nic dla "+trackUrl).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("Wyjątek podczas próby muzycznej: "+exception.getMessage()).queue();
            }
        });
    }

    public void play(@NotNull Guild guild, @NotNull GuildMusicContainer musicContainer, AudioTrack track) {
        //connectToFirstVoiceChannel(guild.getAudioManager());
        musicContainer.scheduler.queue(track);
    }

    public void skipTrack(@NotNull TextChannel channel) {
        GuildMusicContainer musicManager = getGuildContainer(channel.getGuild());
        musicManager.scheduler.nextTrack();

        channel.sendMessage("Pominięto ten utwór.").queue();
    }
}
