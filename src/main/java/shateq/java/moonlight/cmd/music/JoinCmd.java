package shateq.java.moonlight.cmd.music;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import shateq.java.moonlight.CommandHandler;
import shateq.java.moonlight.util.CommandAdapter;
import shateq.java.moonlight.util.CommandContext;

import java.util.List;

@SuppressWarnings("ConstantConditions")
public class JoinCmd implements CommandAdapter {
    @Override
    public void run(CommandContext ctx) {
        final Member self = ctx.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (selfVoiceState.inAudioChannel()) {
            CommandHandler.commandReply("> **Już połączono mnie z kanałem głosowym.** (" + selfVoiceState.getChannel().getAsMention() + ")", ctx.getEvent());
            return;
        }

        final Member member = ctx.getEvent().getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inAudioChannel()) {
            CommandHandler.commandReply("> **Musisz połączyć się z kanałem głosowym do którego mam dołączyć!**", ctx.getEvent());
            return;
        }

        if (!self.hasPermission(Permission.VOICE_CONNECT)) {
            CommandHandler.missingPerms(Permission.VOICE_CONNECT, ctx.getEvent());
            return;
        }

        final AudioManager audioManager = ctx.getGuild().getAudioManager();
        audioManager.openAudioConnection(memberVoiceState.getChannel());
        CommandHandler.commandReply("> \uD83C\uDFA7 **Łączenie z " + memberVoiceState.getChannel().getAsMention() + "**..", ctx.getEvent());
    }

    @Override
    public MessageReceivedEvent getEvent() {
        return null;
    }

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public List<String> getHelp() {
        return List.of("Przywołuje bota do twojego kanału", "join");
    }

    @Override
    public List<String> getAliases() {
        return List.of("summon");
    }
}
