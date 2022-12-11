package shateq.moonlight.cmd.musc;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.dispatcher.Command;
import shateq.moonlight.dispatcher.GuildContext;
import shateq.moonlight.util.Replies;

@SuppressWarnings("ConstantConditions")
public class JoinCmd implements Command {
    @Override
    public void execute(@NotNull GuildContext ctx) {
        final Member self = ctx.event().getGuild().getSelfMember();
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

        final AudioManager audioManager = ctx.event().getGuild().getAudioManager();
        audioManager.openAudioConnection(memberVoiceState.getChannel());
        Replies.commandReply("> \uD83C\uDFA7 **Łączenie z " + memberVoiceState.getChannel().getAsMention() + "**..", ctx.event());
    }
}
