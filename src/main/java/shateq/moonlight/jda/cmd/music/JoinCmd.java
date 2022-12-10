package shateq.moonlight.jda.cmd.music;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import shateq.moonlight.cmd.CommandWrapper;
import shateq.moonlight.cmd.CommandContext;
import shateq.moonlight.util.Replies;

import java.util.List;

@SuppressWarnings("ConstantConditions")
public class JoinCmd implements CommandWrapper {
    @Override
    public void run(CommandContext ctx) {
        final Member self = ctx.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (selfVoiceState.inAudioChannel()) {
            Replies.commandReply("> **Już połączono mnie z kanałem głosowym.** (" + selfVoiceState.getChannel().getAsMention() + ")", ctx.event());
            return;
        }

        final Member member = ctx.event().getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inAudioChannel()) {
            Replies.commandReply("> **Musisz połączyć się z kanałem głosowym do którego mam dołączyć!**", ctx.event());
            return;
        }

        if (!self.hasPermission(Permission.VOICE_CONNECT)) {
            Replies.missingPerms(Permission.VOICE_CONNECT, ctx.event());
            return;
        }

        final AudioManager audioManager = ctx.getGuild().getAudioManager();
        audioManager.openAudioConnection(memberVoiceState.getChannel());
        Replies.commandReply("> \uD83C\uDFA7 **Łączenie z " + memberVoiceState.getChannel().getAsMention() + "**..", ctx.event());
    }

    @Override
    public MessageReceivedEvent event() {
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
