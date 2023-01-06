package shateq.moonlight.mod;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.CustomEmoji;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.MoonlightBot;
import shateq.moonlight.mod.api.Identifier;
import shateq.moonlight.mod.api.Module;
import shateq.moonlight.mod.api.ModuleStatus;
import shateq.moonlight.util.Orbit;

import java.util.regex.Pattern;

public class DetectionMod extends Module {
    public DetectionMod(Identifier id, ModuleStatus status) {
        super(id, status);
    }

    @Override
    public void init() {
        super.init();
        MoonlightBot.jda().addEventListener(this);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent e) {
        Message msg = e.getMessage();
        if (Orbit.complainsURL(msg.getContentDisplay())) {
            Site target = match(msg.getContentDisplay());
            if (target == Site.Other) return;

            target.react(msg);
        }
    }

    private @NotNull Site match(String link) {
        for (Site site : Site.values())
            if (!site.equals(Site.Other))
                if (site.getPattern().matcher(link).find())
                    return site;
        return Site.Other;
    }

    public enum Site {
        YouTube(Pattern.compile("(?:https?:\\/\\/)?(?:(?:www\\.?)?youtube\\.com)|(?:youtu\\.be(\\/.*)?)"), Emoji.fromCustom("yt", 838741674598465586L, false)),
        Spotify(Pattern.compile("(?:https?:\\/\\/)?(open.|play.)spotify.com"), Emoji.fromCustom("sp", 838741674591125504L, false)),
        Reddit(Pattern.compile("(?:https?:\\/\\/)?(?:www\\.)?(redd(it.com|.it))"), Emoji.fromCustom("rd", 838741674628743209L, false)),
        Discord(Pattern.compile("(?:https?:\\/\\/)?(discord(?:app)?\\.com)"), Emoji.fromCustom("dc", 838735948124651550L, false)),
        Other(null, null);

        private final Pattern pattern;
        private final CustomEmoji emoji;

        Site(Pattern pattern, CustomEmoji emoji) {
            this.pattern = pattern;
            this.emoji = emoji;
        }

        public Pattern getPattern() {
            return pattern;
        }

        public void react(@NotNull Message msg) {
            msg.addReaction(this.emoji).queue();
        }
    }
}
