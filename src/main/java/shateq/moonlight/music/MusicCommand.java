package shateq.moonlight.music;

import org.jetbrains.annotations.NotNull;
import shateq.moonlight.dispatcher.api.Command;
import shateq.moonlight.dispatcher.GuildContext;
import shateq.moonlight.util.Reply;

public interface MusicCommand extends Command {
    // TODO REDO
    default boolean connectionEquals(@NotNull GuildContext c) {
        var member = c.member().getVoiceState();
        var self = c.guild().getSelfMember().getVoiceState();
        if (member == null || self == null) return false;

        return member.getChannel().getId().equals(self.getChannel().getId());
    }

    default boolean selfConnected(@NotNull GuildContext c) {
        var self = c.guild().getSelfMember().getVoiceState();
        if (self != null && !self.inAudioChannel()) {
            Reply.A.quote("Muszę być na kanale głosowym, by to zadziałało!", c.event()).queue();
            return false;
        }
        return true;
    }

    default boolean memberConnected(@NotNull GuildContext c) {
        var member = c.member().getVoiceState();
        if (member != null && !member.inAudioChannel()) {
            Reply.A.quote("Nie znam kanału do którego jesteś połączony(-a).", c.event()).queue();
            return false;
        }
        return true;
    }
}
