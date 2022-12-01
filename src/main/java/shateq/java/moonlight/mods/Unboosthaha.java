package shateq.java.moonlight.mods;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import org.jetbrains.annotations.NotNull;
import shateq.java.moonlight.MoonlightBot;
import shateq.java.moonlight.util.Status;

public class Unboosthaha {
    private Status status;


    public void onGuildMemberRoleRemove(final @NotNull GuildMemberRoleRemoveEvent e) {

        if (e.getUser().isBot()) return;
        Role nitro = e.getGuild().getRoleById("716200497526341635");
        String m = e.getMember().getAsMention();
        if (!e.getRoles().contains(nitro)) return;

        TextChannel txt = e.getGuild().getTextChannelById(MoonlightBot.env("BOOST"));
        if (txt != null) {
            txt.sendMessage("> " + m + " już nie ulepsza serwera!").queue();
        }
    }

}
