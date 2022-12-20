package shateq.moonlight.cmd;

import org.jetbrains.annotations.NotNull;
import shateq.moonlight.MoonlightBot;
import shateq.moonlight.dispatcher.Command;
import shateq.moonlight.dispatcher.GuildContext;
import shateq.moonlight.dispatcher.Order;
import shateq.moonlight.mod.Module;
import shateq.moonlight.mod.ModuleStatus;
import shateq.moonlight.util.Messages;

@Order("modules")
@Order.Aliases({"module", "mods"})
@Order.Explanation("List modules")
public class ModulesCmd implements Command {
    @Override
    public void execute(@NotNull GuildContext c) {
        systems(c);
    }

    private void systems(GuildContext c) {
        var modules = MoonlightBot.moduleChute().modules();
        int len = 0;
        for (String id : modules.keySet()) {
            if (id.length() > len) len = id.length();
        }

        StringBuilder msg = new StringBuilder();
        for (Module md : modules.values()) {
            var id = md.id;
            if (id.length() < len) {
                id += " ".repeat(len - id.length());
            }

            msg.append("`").append(id).append("` ").append(md.status.mark()).append(" ").append(md.name).append("\n");
        }

        var help = Messages.Replies.authoredEmbed(c.sender(), true).setTitle("• Moduły (" + modules.size() + ")")
            .setDescription(msg)
            .addField("Legenda", ModuleStatus.Companion.getNote(), false)
            .build();
        Messages.Replies.embed(help, c.event()).queue();
    }
}
