package shateq.moonlight.util;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.jda.MoonlightBot;

public class Replies {
    public static void commandReply(final CharSequence msg, final @NotNull MessageReceivedEvent e) {
        e.getChannel().sendMessage(msg).setMessageReference(e.getMessage()).queue();
    }

    public static void commandEmbed(final MessageEmbed msg, final @NotNull MessageReceivedEvent e) {
        e.getChannel().sendMessageEmbeds(msg).setMessageReference(e.getMessage()).queue();
    }

    public static void missingArgs(final String help, final MessageReceivedEvent e) {
        commandReply("> Brak wszystkich argumentów.\n**Poprawne użycie:** `" + MoonlightBot.Const.PREFIX + help + "`.", e);
    }

    public static void missingPerms(final @NotNull Permission perm, final MessageReceivedEvent e) {
        commandReply("> Brakujące uprawnienia.\n**Kod:** `" + perm.getName() + "`.", e);
    }
}
