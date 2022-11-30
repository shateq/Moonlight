package shateq.java.moonlight.mods;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import shateq.java.moonlight.Main;
import shateq.java.moonlight.util.Module;
import shateq.java.moonlight.util.Status;

public class Unboosthaha  {
    private Status status;


    public void onGuildMemberRoleRemove(final @NotNull GuildMemberRoleRemoveEvent e) {

        if(e.getUser().isBot()) return;
        Role nitro = e.getGuild().getRoleById("716200497526341635");
        String m = e.getMember().getAsMention();
        if(!e.getRoles().contains(nitro)) return;

        TextChannel txt = e.getGuild().getTextChannelById(Main.get("BOOST"));
        if(txt != null) {
            txt.sendMessage("> "+m+" już nie ulepsza serwera!").queue();
        }
    }

}
