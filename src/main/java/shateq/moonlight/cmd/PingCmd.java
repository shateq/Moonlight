package shateq.moonlight.cmd;

import org.jetbrains.annotations.NotNull;
import shateq.moonlight.dispatcher.GuildContext;
import shateq.moonlight.dispatcher.api.Command;
import shateq.moonlight.dispatcher.api.Order;

@Order(value = "ping", note = "Pong!")
public class PingCmd implements Command {
    @Override
    public void execute(@NotNull GuildContext c) {
        c.jda().getRestPing().queue(
            ping -> c.source().reply("**Ping:** `" + ping + " ms`\n**Ping API:** `" + c.jda().getGatewayPing() + " ms`").queue()
        );
    }
}
