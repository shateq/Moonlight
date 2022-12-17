package shateq.moonlight.cmd.funky;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.dispatcher.Category;
import shateq.moonlight.dispatcher.GuildContext;
import shateq.moonlight.dispatcher.Order;
import shateq.moonlight.music.MusicCommand;

@Order("stop")
@Order.Aliases("leave")
@Order.Explanation("Stop music player")
@Order.Rank(Category.Music)
public class StopCmd implements MusicCommand {
    @Override
    public void execute(@NotNull GuildContext c) {
        final Member self = c.event().getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (this.selfConnected(c)) {
            return;
        }

        final Member member = c.event().getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();
    }
}
