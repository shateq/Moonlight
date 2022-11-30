package shateq.java.moonlight.cmd;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shateq.java.moonlight.CommandHandler;
import shateq.java.moonlight.util.CommandAdapter;
import shateq.java.moonlight.util.CommandContext;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GoogleCmd implements CommandAdapter {
    @Override
    public void run(CommandContext ctx) {
        if (ctx.getArgs().isEmpty()) {
            CommandHandler.missingArgs(getHelp().get(1), ctx.getEvent());
            return;
        }
        String args = String.join(" ", ctx.getArgs());
        String url = "<https://letmegooglethat.com/?q=" + URLEncoder.encode(args, StandardCharsets.UTF_8) + ">";
        CommandHandler.commandReply(url, ctx.getEvent());
    }

    @Override
    public MessageReceivedEvent getEvent() {
        return null;
    }

    @Override
    public String getName() {
        return "google";
    }

    @Override
    public List<String> getHelp() {
        return List.of("Wkleja podaną frazę w Google.", "google <Fraza:String>");
    }
}
