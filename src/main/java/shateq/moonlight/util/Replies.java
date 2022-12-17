package shateq.moonlight.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.MoonlightBot;

/**
 * Common replies
 */
public class Replies {
    public static @NotNull EmbedBuilder authoredEmbed(@NotNull User author, boolean normal) {
        return new EmbedBuilder().setAuthor(author.getAsTag(), null, author.getEffectiveAvatarUrl()).setColor(normal ? MoonlightBot.Const.NORMAL : MoonlightBot.Const.BAD);
    }

    @Contract("_, _ -> new")
    public static @NotNull MessageCreateAction quote(CharSequence message, @NotNull MessageReceivedEvent e) {
        return e.getChannel().sendMessage(">" + message).setMessageReference(e.getMessage());
    }

    @Contract("_, _ -> new")
    public static @NotNull MessageCreateAction simply(CharSequence msg, @NotNull MessageReceivedEvent e) {
        return e.getChannel().sendMessage(msg).setMessageReference(e.getMessage());
    }

    @Contract("_, _ -> new")
    public static @NotNull MessageCreateAction embed(MessageEmbed msg, @NotNull MessageReceivedEvent e) {
        return e.getChannel().sendMessageEmbeds(msg).setMessageReference(e.getMessage());
    }

    public static void missingArgs(String help, MessageReceivedEvent e) {
        simply("> Brak wszystkich argumentów.\n**Poprawne użycie:** `" + MoonlightBot.Const.PREFIX + help + "`.", e);
    }

    public static void missingPerms(@NotNull Permission perm, MessageReceivedEvent e) {
        simply("> Brakujące uprawnienia.\n**Kod:** `" + perm.getName() + "`.", e);
    }
}
