package shateq.moonlight.cmd;

import org.jetbrains.annotations.NotNull;
import shateq.moonlight.MoonlightBot;
import shateq.moonlight.dispatcher.Command;
import shateq.moonlight.dispatcher.GuildContext;
import shateq.moonlight.dispatcher.Order;
import shateq.moonlight.modules.Module;
import shateq.moonlight.util.Util;

@Order("modules")
@Order.Aliases("module")
@Order.Explanation("List modules")
public class ModulesCmd implements Command {
    @Override
    public void execute(@NotNull GuildContext c) {
        var modules = MoonlightBot.moduleChute().modules();

        StringBuilder msg = new StringBuilder();
        for (Module md : modules.values()) {
            msg.append("`" + md.id + "` " + md.status.mark + " " + md.name + "\n");
        }

        var help = Util.Replies.authoredEmbed(c.sender(), true).setTitle("• Moduły (" + modules.size() + ")")
            .setDescription(msg + "\n🟢 Włączone\n🟡 Wyłączone\n🔴 Niedostępne\n🔵 Wbudowane")
            .build();
        Util.Replies.embed(help, c.event()).queue();
    }
}
