package shateq.moonlight.mod;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;
import shateq.moonlight.MoonlightBot;
import shateq.moonlight.mod.api.Identifier;
import shateq.moonlight.mod.api.Module;
import shateq.moonlight.mod.api.ModuleStatus;
import shateq.moonlight.mod.api.Place;

public class BoostMod extends Module {
    public BoostMod(Identifier id, ModuleStatus status) {
        super(id, status);
    }

    public void logBoost(String output, @NotNull Guild guild) {
        assert guild.getBoostRole() != null;
        if (guild.getRoles().contains(guild.getBoostRole())) {
            var channel = guild.getSystemChannel(); // TODO database
            if (channel == null) {
                MoonlightBot.LOG.warn("BoostMod Log's incorrectly configured for guild " + new Place(guild) + ", maybe the module should be disabled?");
                return;
            }
            channel.sendMessage(output).queue();
        }
    }

    @Override
    public void init() {
        super.init();
        MoonlightBot.jda().addEventListener(this);
    }

    @Override
    public void onGuildMemberRoleAdd(@NotNull GuildMemberRoleAddEvent event) {
        Mono.create(sink ->
            logBoost(event.getMember().getAsMention() + " ulepsza ten serwer!", event.getGuild())
        ).subscribe();
    }

    @Override
    public void onGuildMemberRoleRemove(@NotNull GuildMemberRoleRemoveEvent event) {
        Mono.create(sink ->
            logBoost(event.getMember().getAsMention() + " przestaje ulepszać ten serwer!", event.getGuild())
        ).subscribe();
    }
}
