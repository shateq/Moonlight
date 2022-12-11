package shateq.moonlight.cmd;

import org.jetbrains.annotations.NotNull;
import shateq.moonlight.dispatcher.Command;
import shateq.moonlight.dispatcher.GuildContext;
import shateq.moonlight.dispatcher.Order;
import shateq.moonlight.jda.MoonlightBot;
import shateq.moonlight.modules.Module;
import shateq.moonlight.util.Replies;

@Order("modules")
@Order.Aliases("module")
@Order.Explanation("List modules")
public class ModulesCmd implements Command {
    @Override
    public void execute(@NotNull GuildContext c) {
        var modules = MoonlightBot.modules().modules();

        StringBuilder msg = new StringBuilder();
        for (Module md : modules.values()) {
            msg.append("`" + md.id + "` " + md.status.mark + " " + md.name + "\n");
        }

        var help = Replies.authoredEmbed(c.sender(), true).setTitle("• Moduły (" + modules.size() + ")")
            .setDescription(msg + "\n🟢 Włączone\n🟡 Wyłączone\n🔴 Niedostępne\n🔵 Wbudowane")
            .build();
        Replies.commandEmbed(help, c.event());
    }
}
