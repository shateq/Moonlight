package shateq.moonlight.jda.cmd;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shateq.moonlight.cmd.Command;
import shateq.moonlight.cmd.CommandWrapper;
import shateq.moonlight.cmd.CommandContext;
import shateq.moonlight.util.Replies;

import java.util.List;

public class PingCmd extends Command implements CommandWrapper {

    @Override
    public void run(CommandContext ctx) {
        ctx.getJDA().getRestPing().queue((ping) -> Replies.commandReply("> **Ping:** `" + ping + " ms`\n> **Ping API:** `" + ctx.getJDA().getGatewayPing() + " ms`", ctx.event()));
    }

    @Override
    public MessageReceivedEvent event() {
        return null;
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public List<String> getHelp() {
        return List.of("Pong!", "ping");
    }
}
