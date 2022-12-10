package shateq.moonlight.jda.cmd;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.jda.MoonlightBot;
import shateq.moonlight.cmd.CommandWrapper;
import shateq.moonlight.cmd.CommandContext;
import shateq.moonlight.modules.Module;
import shateq.moonlight.util.Replies;

import java.util.Collection;
import java.util.List;

public class ModuleCmd implements CommandWrapper {
    @Override
    public void run(@NotNull CommandContext ctx) {
        final Collection<Module> modules = MoonlightBot.modules().showModules();
        final User author = ctx.event().getAuthor();

        final StringBuilder msg = new StringBuilder();
        for (Module md : modules) {
            msg.append("`").append(md.id).append("` ").append(md.status.mark).append(" ").append(md.name).append("\n");
        }

        MessageEmbed help = new EmbedBuilder().setColor(MoonlightBot.Const.BAD).setTitle("• Moduły (" + modules.size() + ")").setAuthor(author.getAsTag(), null, author.getEffectiveAvatarUrl())
            .setDescription(msg + "\n🟢 Włączone\n🟡 Wyłączone\n🔴 Niedostępne\n🔵 Wbudowane").build();
        Replies.commandEmbed(help, ctx.event());
    }

    @Override
    public MessageReceivedEvent event() {
        return null;
    }

    @Override
    public String getName() {
        return "modules";
    }

    @Override
    public List<String> getHelp() {
        return List.of("Lista modułów", "modules");
    }

    @Override
    public List<String> getAliases() {
        return List.of("module");
    }
}
