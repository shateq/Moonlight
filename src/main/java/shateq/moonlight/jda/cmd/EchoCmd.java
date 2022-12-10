package shateq.moonlight.jda.cmd;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.cmd.CommandWrapper;
import shateq.moonlight.cmd.CommandContext;
import shateq.moonlight.util.Replies;

import java.util.List;

public class EchoCmd implements CommandWrapper {
    @Override
    public void run(@NotNull CommandContext ctx) {
        if (ctx.args().isEmpty()) {
            Replies.missingArgs(getHelp().get(1), ctx.event());
            return;
        }
        User author = ctx.event().getAuthor();
        String msg = String.join(" ", ctx.args());
        ctx.getChannel().sendMessageFormat("%s \n\n**~ %s (%s)**", msg, author.getAsMention(), author.getAsTag()).queue();
    }

    @Override
    public MessageReceivedEvent event() {
        return null;
    }

    @Override
    public String getName() {
        return "echo";
    }

    @Override
    public List<String> getHelp() {
        return List.of("Echo, echo, echo..", "echo <Tekst:String>");
    }
}
