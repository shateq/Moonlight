package shateq.java.moonlight.cmd;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shateq.java.moonlight.CommandHandler;
import shateq.java.moonlight.util.CommandAdapter;
import shateq.java.moonlight.util.CommandContext;

import java.util.List;

public class PingCmd implements CommandAdapter {

    @Override
    public void run(CommandContext ctx) {
        ctx.getJDA().getRestPing().queue((ping) -> CommandHandler.commandReply("> **Ping:** `" + ping + " ms`\n> **Ping API:** `" + ctx.getJDA().getGatewayPing() + " ms`", ctx.getEvent()));
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
