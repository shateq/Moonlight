package shateq.moonlight.cmd.musc;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.dispatcher.Command;
import shateq.moonlight.dispatcher.GuildContext;
import shateq.moonlight.util.Replies;

@SuppressWarnings("ConstantConditions")
public class LeaveCmd implements Command {
    @Override
    public void execute(@NotNull GuildContext ctx) {
        final Member self = ctx.event().getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inAudioChannel()) {
            Replies.commandReply("> **Komenda nie zadziała, ponieważ nie połączono mnie z kanałem głosowym.**", ctx.event());
            return;
        }

        final Member member = ctx.event().getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

    }
}
