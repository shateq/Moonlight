package shateq.moonlight.cmd;

import org.jetbrains.annotations.NotNull;
import shateq.moonlight.dispatcher.api.Command;
import shateq.moonlight.dispatcher.GuildContext;
import shateq.moonlight.dispatcher.api.Order;
import shateq.moonlight.util.Messages;

@Order("ping")
@Order.Explanation("Pong!")
public class PingCmd implements Command {
    @Override
    public void execute(@NotNull GuildContext c) {
        c.jda().getRestPing().queue(ping -> Messages.Replies.just("> **Ping:** `" + ping + " ms`\n> **Ping API:** `" + c.jda().getGatewayPing() + " ms`", c.event()).queue());
    }
}
