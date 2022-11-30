package shateq.java.moonlight.util;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.internal.utils.Checks;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandContext implements CommandAdapter {
    private final GuildMessageReceivedEvent event;
    private final List<String> args;

    public CommandContext(GuildMessageReceivedEvent event, List<String> args) {
        Checks.notNull(event, "event");
        Checks.notNull(args, "args");

        this.event = event;
        this.args = args;
    }

    @Override
    public void run(@NotNull CommandContext ctx) {}

    @Override
    public Guild getGuild() {
        return this.getEvent().getGuild();
    }

    @Override
    public GuildMessageReceivedEvent getEvent() {
        return this.event;
    }

    @Override
    public String getName() {
        return null;
    }

    public List<String> getArgs() {
        return this.args;
    }

    public List<String> getHelp() {return null;}

}
