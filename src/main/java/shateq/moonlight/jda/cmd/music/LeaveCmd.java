package shateq.moonlight.jda.cmd.music;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shateq.moonlight.cmd.CommandWrapper;
import shateq.moonlight.cmd.CommandContext;
import shateq.moonlight.util.Replies;

import java.util.List;

@SuppressWarnings("ConstantConditions")
public class LeaveCmd implements CommandWrapper {
    @Override
    public void run(CommandContext ctx) {
        final Member self = ctx.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inAudioChannel()) {
            Replies.commandReply("> **Komenda nie zadziała, ponieważ nie połączono mnie z kanałem głosowym.**", ctx.event());
            return;
        }

        final Member member = ctx.event().getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

    }

    @Override
    public MessageReceivedEvent event() {
        return null;
    }

    @Override
    public String getName() {
        return "disconnect";
    }

    @Override
    public List<String> getHelp() {
        return List.of("Wygania bota z twojego kanału", "leave");
    }

    @Override
    public List<String> getAliases() {
        return List.of("dc", "leave");
    }
}
