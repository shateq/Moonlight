package shateq.java.moonlight.cmd;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import shateq.java.moonlight.CommandHandler;
import shateq.java.moonlight.ModuleLauncher;
import shateq.java.moonlight.MoonlightBot;
import shateq.java.moonlight.util.CommandAdapter;
import shateq.java.moonlight.util.CommandContext;
import shateq.java.moonlight.util.Module;

import java.util.Collection;
import java.util.List;

public class ModuleCmd implements CommandAdapter {
    @Override
    public void run(@NotNull CommandContext ctx) {
        final Collection<Module> modules = ModuleLauncher.showModules();
        final User author = ctx.getEvent().getAuthor();

        final StringBuilder msg = new StringBuilder();
        for (Module md : modules) {
            msg.append("`").append(md.getId()).append("` ").append(md.getStatus().mark).append(" ").append(md.getName()).append("\n");
        }

        MessageEmbed help = new EmbedBuilder().setColor(MoonlightBot.BAD).setTitle("• Moduły (" + modules.size() + ")").setAuthor(author.getAsTag(), null, author.getEffectiveAvatarUrl())
            .setDescription(msg + "\n🟢 Włączone\n🟡 Wyłączone\n🔴 Niedostępne\n🔵 Wbudowane").build();
        CommandHandler.commandEmbed(help, ctx.getEvent());
    }

    @Override
    public MessageReceivedEvent getEvent() {
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
