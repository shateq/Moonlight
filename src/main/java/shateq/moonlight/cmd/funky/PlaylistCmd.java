package shateq.moonlight.cmd.funky;

import org.jetbrains.annotations.NotNull;
import shateq.moonlight.dispatcher.api.Category;
import shateq.moonlight.dispatcher.GuildContext;
import shateq.moonlight.dispatcher.api.Order;
import shateq.moonlight.music.MusicCommand;
import shateq.moonlight.util.Messages;

@Order(value = "playlist", group = Category.Music)
@Order.Aliases("tracks")
@Order.Explanation("Return list of tracks")
public class PlaylistCmd implements MusicCommand {
    @Override
    public void execute(@NotNull GuildContext c) {
        Messages.Replies.quote("Not yet", c.event());
    }
}
