package shateq.moonlight.jda.cmd;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shateq.moonlight.jda.OrderGround;
import shateq.moonlight.util.CommandAdapter;
import shateq.moonlight.util.CommandContext;

import java.util.List;

public class PingCmd implements CommandAdapter {

    @Override
    public void run(CommandContext ctx) {
        ctx.getJDA().getRestPing().queue((ping) -> OrderGround.commandReply("> **Ping:** `" + ping + " ms`\n> **Ping API:** `" + ctx.getJDA().getGatewayPing() + " ms`", ctx.getEvent()));
    }

    @Override
    public MessageReceivedEvent getEvent() {
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
