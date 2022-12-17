package shateq.moonlight.music;

import org.jetbrains.annotations.NotNull;
import shateq.moonlight.dispatcher.Command;
import shateq.moonlight.dispatcher.GuildContext;
import shateq.moonlight.util.Util;

public interface MusicCommand extends Command {
    default boolean connectionEquals(@NotNull GuildContext c) {
        var member = c.member().getVoiceState();
        var self = c.guild().getSelfMember().getVoiceState();
        if (member == null || self == null) return false;

        return member.getChannel().getId().equals(self.getChannel().getId());
    }

    default boolean selfConnected(@NotNull GuildContext c) {
        var self = c.guild().getSelfMember().getVoiceState();
        if (self != null && !self.inAudioChannel()) {
            Util.Replies.quote("Muszę być na kanale głosowym, by to zadziałało!", c.event()).queue();
            return false;
        }
        return true;
    }

    default boolean memberConnected(@NotNull GuildContext c) {
        var member = c.member().getVoiceState();
        if (member != null && !member.inAudioChannel()) {
            Util.Replies.quote("Nie znam kanału do którego jesteś połączony(-a).", c.event()).queue();
            return false;
        }
        return true;
    }
}
