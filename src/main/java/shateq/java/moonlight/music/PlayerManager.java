package shateq.java.moonlight.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
    private static PlayerManager INSTANCE;
    private final Map<Long, Jukebox> guilds;
    private final AudioPlayerManager audioManager;

    public PlayerManager() {
        this.guilds = new HashMap<>();
        this.audioManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(this.audioManager);
        AudioSourceManagers.registerLocalSource(this.audioManager);
    }

    public Jukebox getJukebox(Guild guild) {
        return this.guilds.computeIfAbsent(guild.getIdLong(), (id) -> {
            final Jukebox jukebox = new Jukebox(this.audioManager);
            guild.getAudioManager().setSendingHandler(jukebox.getSendHandler());

            return jukebox;
        });
    }

    public void loadTrack(TextChannel channel, String url) {
        final Jukebox jukebox = this.getJukebox(channel.getGuild());
        this.audioManager.loadItemOrdered(jukebox, url, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                jukebox.scheduler.queue(track);

                channel.sendMessage("> \uD83D\uDCFB **Dodano do kolejki..**\n`" + track.getInfo().title + "` - `" + track.getInfo().author + "` **(" + track.getInfo().length + ")**").queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                //
            }

            @Override
            public void noMatches() {
            }

            @Override
            public void loadFailed(FriendlyException exception) {
            }
        });
    }

    public static PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }
        return INSTANCE;
    }
}
