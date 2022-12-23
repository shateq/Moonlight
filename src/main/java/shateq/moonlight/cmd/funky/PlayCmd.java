package shateq.moonlight.cmd.funky;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.dispatcher.api.Category;
import shateq.moonlight.dispatcher.GuildContext;
import shateq.moonlight.dispatcher.api.Order;
import shateq.moonlight.music.MusicCommand;
import shateq.moonlight.util.Messages;

@Order(value = "play", group = Category.Music)
@Order.Aliases("join")
@Order.Explanation("Start music player")
public class PlayCmd implements MusicCommand {
    @Override
    public void execute(@NotNull GuildContext c) {
        if (this.selfConnected(c)) {
            return;
        }

        if (this.memberConnected(c)) {
            return;
        }

        if (!this.connectionEquals(c)) {
            Messages.Replies.quote("Kanały głosowe nie pokrywają się...", c.event());
            return;
        }

        Guild guild = c.guild();
        VoiceChannel ch = guild.getVoiceChannelById(c.member().getVoiceState().getChannel().getId());

        AudioManager manager = guild.getAudioManager();
        manager.openAudioConnection(ch);

        //YAAAAAAAAAAAAAAA
        //PlayerManager.getInstance().loadTrack(ctx.getChannel(), "https://www.youtube.com/watch?v=GHMjD0Lp5DY");
        Messages.Replies.just("Fire!", c.event()).queue();
    }
}
