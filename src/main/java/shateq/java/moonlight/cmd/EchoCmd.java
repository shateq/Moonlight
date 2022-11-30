package shateq.java.moonlight.cmd;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import shateq.java.moonlight.CommandHandler;
import shateq.java.moonlight.util.CommandAdapter;
import shateq.java.moonlight.util.CommandContext;

import java.util.List;

public class EchoCmd implements CommandAdapter {
    @Override
    public void run(@NotNull CommandContext ctx) {
        if(ctx.getArgs().isEmpty()) {
            CommandHandler.missingArgs(getHelp().get(1), ctx.getEvent());
            return;
        }
        User author = ctx.getEvent().getAuthor();
        String msg = String.join(" ", ctx.getArgs());
        ctx.getChannel().sendMessageFormat("%s \n\n**~ %s (%s)**", msg, author.getAsMention(), author.getAsTag()).queue();
    }

    @Override
    public GuildMessageReceivedEvent getEvent() {
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
