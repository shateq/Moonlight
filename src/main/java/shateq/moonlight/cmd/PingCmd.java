package shateq.moonlight.cmd;

import org.jetbrains.annotations.NotNull;
import shateq.moonlight.dispatcher.Command;
import shateq.moonlight.dispatcher.GuildContext;
import shateq.moonlight.dispatcher.Order;
import shateq.moonlight.util.Util;

@Order("ping")
@Order.Explanation("Pong!")
@Order.Rank(Command.Category.Blank)
public class PingCmd implements Command {
    @Override
    public void execute(@NotNull GuildContext c) {
        c.jda().getRestPing().queue(ping -> Util.Replies.simply("> **Ping:** `" + ping + " ms`\n> **Ping API:** `" + c.jda().getGatewayPing() + " ms`", c.event()).queue());
    }
}
