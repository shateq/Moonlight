package shateq.moonlight.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer audioPlayer;
    private final BlockingQueue<AudioTrack> queue;
    AudioTrack lastTrack;
    private boolean repeating = false;

    public TrackScheduler(AudioPlayer player) {
        this.audioPlayer = player;
        this.queue = new LinkedBlockingQueue<>();
    }

    /**
     * Add the next track to queue or play right away if nothing is in the queue.
     */
    public void queue(AudioTrack track) {
        if (!audioPlayer.startTrack(track, true)) {
            //noinspection ResultOfMethodCallIgnored
            queue.offer(track);
        }
    }

    /**
     * Start the next track, stopping the current one if it is playing.
     */
    public void nextTrack() {
        audioPlayer.startTrack(queue.poll(), false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, @NotNull AudioTrackEndReason endReason) {
        this.lastTrack = track;
        if (endReason.mayStartNext) {
            if (repeating)
                player.startTrack(lastTrack.makeClone(), false);
            else
                nextTrack();
        }
    }

    public boolean repeats() {
        return repeating;
    }

    public void setRepeating(boolean repeating) {
        this.repeating = repeating;
    }

    public void shuffle() {
        Collections.shuffle((List<?>) queue);
    }
}
