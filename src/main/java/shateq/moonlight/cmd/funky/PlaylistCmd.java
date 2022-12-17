package shateq.moonlight.cmd.funky;

import org.jetbrains.annotations.NotNull;
import shateq.moonlight.dispatcher.Command;
import shateq.moonlight.dispatcher.GuildContext;
import shateq.moonlight.dispatcher.Order;
import shateq.moonlight.music.MusicCommand;
import shateq.moonlight.util.Util;

@Order("playlist")
@Order.Aliases("tracks")
@Order.Explanation("Return list of tracks")
@Order.Rank(Command.Category.Music)
public class PlaylistCmd implements MusicCommand {
    @Override
    public void execute(@NotNull GuildContext c) {
        Util.Replies.quote("Not yet", c.event());
    }
}
