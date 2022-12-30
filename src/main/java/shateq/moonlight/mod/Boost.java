package shateq.moonlight.mod;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;
import shateq.moonlight.ModuleChute;
import shateq.moonlight.MoonlightBot;
import shateq.moonlight.util.Identifier;
import shateq.moonlight.util.Messages;

public class Boost extends Module {
    public Boost(Identifier id, ModuleStatus status) {
        super(id, status);
    }

    public void logBoost(String output, @NotNull Guild guild) {
        assert guild.getBoostRole() != null;
        if (guild.getRoles().contains(guild.getBoostRole())) {
            var channel = guild.getSystemChannel(); // TODO database
            if (channel == null) {
                ModuleChute.coverage.warn("Boost logs channel incorrectly configured, maybe the module should be disabled?");
                return;
            }
            Messages.Replies.quote(output, channel).queue();
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
