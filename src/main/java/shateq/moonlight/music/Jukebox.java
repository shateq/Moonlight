package shateq.moonlight.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import org.jetbrains.annotations.NotNull;

public class Jukebox {
    public final AudioPlayer audioPlayer;
    public final TrackScheduler scheduler;
    public final SendHandler sendHandler;

    public Jukebox(@NotNull AudioPlayerManager manager) {
        this.audioPlayer = manager.createPlayer();
        this.scheduler = new TrackScheduler(this.audioPlayer);

        this.audioPlayer.addListener(this.scheduler);
        this.sendHandler = new SendHandler(this.audioPlayer);
    }
}
