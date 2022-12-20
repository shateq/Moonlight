package shateq.moonlight.cmd.funky;

import org.jetbrains.annotations.NotNull;
import shateq.moonlight.dispatcher.Category;
import shateq.moonlight.dispatcher.GuildContext;
import shateq.moonlight.dispatcher.Order;
import shateq.moonlight.music.MusicCommand;
import shateq.moonlight.util.Messages;

@Order("playlist")
@Order.Aliases("tracks")
@Order.Explanation("Return list of tracks")
@Order.Rank(Category.Music)
public class PlaylistCmd implements MusicCommand {
    @Override
    public void execute(@NotNull GuildContext c) {
        Messages.Replies.quote("Not yet", c.event());
    }
}
