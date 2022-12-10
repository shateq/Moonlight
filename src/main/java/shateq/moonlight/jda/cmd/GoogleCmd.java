package shateq.moonlight.jda.cmd;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import shateq.moonlight.cmd.CommandWrapper;
import shateq.moonlight.cmd.CommandContext;
import shateq.moonlight.util.Replies;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GoogleCmd implements CommandWrapper {
    @Override
    public void run(CommandContext ctx) {
        if (ctx.args().isEmpty()) {
            Replies.missingArgs(getHelp().get(1), ctx.event());
            return;
        }
        String args = String.join(" ", ctx.args());
        String url = "<https://letmegooglethat.com/?q=" + URLEncoder.encode(args, StandardCharsets.UTF_8) + ">";
        Replies.commandReply(url, ctx.event());
    }

    @Override
    public MessageReceivedEvent event() {
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
