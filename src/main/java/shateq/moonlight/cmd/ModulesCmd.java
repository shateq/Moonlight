package shateq.moonlight.cmd;

import org.jetbrains.annotations.NotNull;
import shateq.moonlight.MoonlightBot;
import shateq.moonlight.dispatcher.Command;
import shateq.moonlight.dispatcher.GuildContext;
import shateq.moonlight.dispatcher.Order;
import shateq.moonlight.mod.Module;
import shateq.moonlight.mod.ModuleStatus;
import shateq.moonlight.util.Util;

@Order("modules")
@Order.Aliases({"module", "mods"})
@Order.Explanation("List modules")
public class ModulesCmd implements Command {
    @Override
    public void execute(@NotNull GuildContext c) {
        var modules = MoonlightBot.moduleChute().modules();
        StringBuilder msg = new StringBuilder(), legend = new StringBuilder();

        for (Module md : modules.values()) {
            msg.append("`").append(md.id).append("` ").append(md.status.mark()).append(" ").append(md.name).append("\n");
        }

        for (ModuleStatus status : ModuleStatus.values()) {
            legend.append(status.mark()).append(" - ").append(status.legend()).append("\n");
        }

        var help = Util.Replies.authoredEmbed(c.sender(), true).setTitle("• Moduły (" + modules.size() + ")")
            .setDescription(msg)
            .addField("Legenda", legend.toString(), false)
            .build();
        Util.Replies.embed(help, c.event()).queue();
    }
}
