package shateq.java.moonlight.cmd.music;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import shateq.java.moonlight.CommandHandler;
import shateq.java.moonlight.util.CommandAdapter;
import shateq.java.moonlight.util.CommandContext;

import java.util.List;

@SuppressWarnings("ConstantConditions")
public class LeaveCmd implements CommandAdapter {
    @Override
    public void run(CommandContext ctx) {
        final Member self = ctx.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if(!selfVoiceState.inVoiceChannel()) {
            CommandHandler.commandReply("> **Komenda nie zadziała, ponieważ nie połączono mnie z kanałem głosowym.**", ctx.getEvent());
            return;
        }

        final Member member = ctx.getEvent().getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

    }

    @Override
    public GuildMessageReceivedEvent getEvent() {
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
