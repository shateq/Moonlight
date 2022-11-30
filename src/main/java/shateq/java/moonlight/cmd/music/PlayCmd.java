package shateq.java.moonlight.cmd.music;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import shateq.java.moonlight.CommandHandler;
import shateq.java.moonlight.music.PlayerManager;
import shateq.java.moonlight.util.CommandAdapter;
import shateq.java.moonlight.util.CommandContext;

import java.util.List;

@SuppressWarnings("ConstantConditions")
public class PlayCmd implements CommandAdapter {
    @Override
    public void run(CommandContext ctx) {
        final Member self = ctx.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if(!selfVoiceState.inVoiceChannel()) {
            CommandHandler.commandReply("> **Muszę być na kanale głosowym, by to zadziałało!**", ctx.getEvent());
            return;
        }

        final Member member = ctx.getEvent().getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if(!memberVoiceState.inVoiceChannel()) {
            CommandHandler.commandReply("> **Musisz być na kanale głosowym, by to zadziałało!**", ctx.getEvent());
            return;
        }

        if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            CommandHandler.commandReply("> **Nie jesteś, na tym samym kanale głosowym co ja!**", ctx.getEvent());
            return;
        }

        PlayerManager.getInstance().loadTrack(ctx.getChannel(), "https://www.youtube.com/watch?v=GHMjD0Lp5DY");
        CommandHandler.commandReply("> **Fire!**", ctx.getEvent());
    }

    @Override
    public GuildMessageReceivedEvent getEvent() {
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
