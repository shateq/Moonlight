package shateq.moonlight.cmd;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.MoonlightBot;
import shateq.moonlight.dispatcher.Dispatcher;
import shateq.moonlight.dispatcher.GuildContext;
import shateq.moonlight.dispatcher.api.Command;
import shateq.moonlight.dispatcher.api.CommandContext;
import shateq.moonlight.dispatcher.api.Order;
import shateq.moonlight.mod.api.Module;
import shateq.moonlight.mod.api.ModuleStatus;
import shateq.moonlight.util.Orbit;

@Order(value = "modules", note = "List of modules.")
@Order.Aliases({"module", "mods", "mod"})
public class ModulesCmd implements Command {
    public ModulesCmd() {
        Dispatcher.upsertCommandData(Commands.slash("modules", "Are systems operational?").setGuildOnly(true));
    }

    @Override
    public void execute(@NotNull GuildContext c) {
        c.source().replyEmbeds(systems(c)).queue();
    }

    public MessageEmbed systems(CommandContext<?, ?> c) {
        var modules = MoonlightBot.moduleChute().modules();
        int len = 0;
        for (String id : modules.keySet()) {
            if (id.length() > len)
                len = id.length();
        }

        StringBuilder msg = new StringBuilder();
        for (Module md : modules.values()) {
            String id = md.id;
            if (id.length() < len) {
                id += " ".repeat(len - id.length());
            }
            msg.append("`").append(id).append("` ").append(md.status.mark()).append(" ").append(md.name).append("\n");
        }

        var help = Orbit.colourEmbed(true).setAuthor(c.sender().getAsTag())
            .setTitle("• Moduły (" + modules.size() + ")")
            .setDescription(msg)
            .addField("Legenda", ModuleStatus.Companion.getNote(), false);

        return help.build();
    }
}
