package shateq.moonlight.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.MoonlightBot;

public class Replies {
    public static @NotNull EmbedBuilder authoredEmbed(@NotNull User author, boolean normal) {
        return new EmbedBuilder().setAuthor(author.getAsTag(), null, author.getEffectiveAvatarUrl()).setColor(normal ? MoonlightBot.Const.NORMAL : MoonlightBot.Const.BAD);
    }

    public static void commandReply(CharSequence msg, @NotNull MessageReceivedEvent e) {
        e.getChannel().sendMessage(msg).setMessageReference(e.getMessage()).queue();
    }

    public static void commandEmbed(MessageEmbed msg, @NotNull MessageReceivedEvent e) {
        e.getChannel().sendMessageEmbeds(msg).setMessageReference(e.getMessage()).queue();
    }

    public static void missingArgs(String help, MessageReceivedEvent e) {
        commandReply("> Brak wszystkich argumentów.\n**Poprawne użycie:** `" + MoonlightBot.Const.PREFIX + help + "`.", e);
    }

    public static void missingPerms(@NotNull Permission perm, MessageReceivedEvent e) {
        commandReply("> Brakujące uprawnienia.\n**Kod:** `" + perm.getName() + "`.", e);
    }
}
