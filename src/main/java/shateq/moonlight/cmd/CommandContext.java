package shateq.moonlight.cmd;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.internal.utils.Checks;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record CommandContext(MessageReceivedEvent event, List<String> args) implements CommandWrapper {
    public CommandContext(@NotNull MessageReceivedEvent event, List<String> args) {
        Checks.notNull(event, "event");
        Checks.notNull(args, "args");

        this.event = event;
        this.args = args;
    }

    @Override
    public void run(@NotNull CommandContext ctx) {
    }

    @Override
    public Guild getGuild() {
        return this.event().getGuild();
    }

    @Override
    public String getName() {
        return null;
    }

    public List<String> getHelp() {
        return null;
    }

}
