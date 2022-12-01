package shateq.moonlight.jda.cmd.music;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shateq.moonlight.jda.OrderGround;
import shateq.moonlight.music.PlayerManager;
import shateq.moonlight.util.CommandAdapter;
import shateq.moonlight.util.CommandContext;

import java.util.List;

@SuppressWarnings("ConstantConditions")
public class PlayCmd implements CommandAdapter {
    @Override
    public void run(CommandContext ctx) {
        final Member self = ctx.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inAudioChannel()) {
            OrderGround.commandReply("> **Muszę być na kanale głosowym, by to zadziałało!**", ctx.getEvent());
            return;
        }

        final Member member = ctx.getEvent().getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inAudioChannel()) {
            OrderGround.commandReply("> **Musisz być na kanale głosowym, by to zadziałało!**", ctx.getEvent());
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            OrderGround.commandReply("> **Nie jesteś, na tym samym kanale głosowym co ja!**", ctx.getEvent());
            return;
        }

        PlayerManager.getInstance().loadTrack(ctx.getChannel(), "https://www.youtube.com/watch?v=GHMjD0Lp5DY");
        OrderGround.commandReply("> **Fire!**", ctx.getEvent());
    }

    @Override
    public MessageReceivedEvent getEvent() {
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
