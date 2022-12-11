//package shateq.moonlight.modules.music;
//
//import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
//import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
//
//public class Jukebox {
//    public final AudioPlayer audio;
//    public final TrackScheduler scheduler;
//
//    private final PlayerSendHandler handler;
//
//    public Jukebox(final AudioPlayerManager manager) {
//        this.audio = manager.createPlayer();
//        this.scheduler = new TrackScheduler(this.audio);
//        this.audio.addListener(this.scheduler);
//        this.handler = new PlayerSendHandler(this.audio);
//    }
//
//    public final PlayerSendHandler getSendHandler() {
//        return handler;
//    }
//}
