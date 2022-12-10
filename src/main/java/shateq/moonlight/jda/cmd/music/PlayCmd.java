package shateq.moonlight.jda.cmd.music;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shateq.moonlight.cmd.CommandWrapper;
import shateq.moonlight.cmd.CommandContext;
import shateq.moonlight.util.Replies;

import java.util.List;

@SuppressWarnings("ConstantConditions")
public class PlayCmd implements CommandWrapper {
    @Override
    public void run(CommandContext ctx) {
        final Member self = ctx.getGuild().getSelfMember();
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

    @Override
    public MessageReceivedEvent event() {
        return null;
    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public List<String> getHelp() {
        return List.of("Rozpoczyna podany przez ciebie utwór z YouTube.", "play <Utwór:Link>");
    }

    @Override
    public List<String> getAliases() {
        return List.of("p");
    }
}
