//package shateq.moonlight.modules.music;
//
//import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
//import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
//import net.dv8tion.jda.api.audio.AudioSendHandler;
//
//import java.nio.ByteBuffer;
//
//public class PlayerSendHandler implements AudioSendHandler {
//    private final AudioPlayer audio;
//    private final ByteBuffer buffer;
//    private final MutableAudioFrame frame;
//
//    public PlayerSendHandler(final AudioPlayer player) {
//        this.audio = player;
//        this.buffer = ByteBuffer.allocate(1024);
//        this.frame = new MutableAudioFrame();
//        this.frame.setBuffer(buffer);
//    }
//
//    @Override
//    public boolean canProvide() {
//        return this.audio.provide(this.frame);
//    }
//
//    @Override
//    public ByteBuffer provide20MsAudio() {
//        return this.buffer.flip();
//    }
//
//    @Override
//    public boolean isOpus() {
//        return true;
//    }
//}
