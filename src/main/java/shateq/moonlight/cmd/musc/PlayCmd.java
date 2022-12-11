package shateq.moonlight.cmd.musc;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.dispatcher.Command;
import shateq.moonlight.dispatcher.GuildContext;
import shateq.moonlight.util.Replies;

@SuppressWarnings("ConstantConditions")
public class PlayCmd implements Command {
    @Override
    public void execute(@NotNull GuildContext ctx) {
        final Member self = ctx.event().getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inAudioChannel()) {
            Replies.commandReply("> **Muszę być na kanale głosowym, by to zadziałało!**", ctx.event());
            return;
        }

        final Member member = ctx.event().getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inAudioChannel()) {
            Replies.commandReply("> **Musisz być na kanale głosowym, by to zadziałało!**", ctx.event());
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            Replies.commandReply("> **Nie jesteś, na tym samym kanale głosowym co ja!**", ctx.event());
            return;
        }

        //PlayerManager.getInstance().loadTrack(ctx.getChannel(), "https://www.youtube.com/watch?v=GHMjD0Lp5DY");
        Replies.commandReply("> **Fire!**", ctx.event());
    }
}
