package shateq.moonlight.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import org.jetbrains.annotations.NotNull;

/**
 * Both the player and the track scheduler for one guild.
 */
public class GuildMusicContainer {
    public final AudioPlayer audioPlayer;
    public final TrackScheduler scheduler;

    public GuildMusicContainer(@NotNull AudioPlayerManager manager) {
        audioPlayer = manager.createPlayer();
        scheduler = new TrackScheduler(audioPlayer);
        audioPlayer.addListener(scheduler);
    }

    /**
     * Use AudioPlayer as an PlayerSendHandler
     *
     * @return PlayerSendHandler implementation
     */
    public PlayerSendHandler getSendHandler() {
        return new PlayerSendHandler(audioPlayer);
    }
}
