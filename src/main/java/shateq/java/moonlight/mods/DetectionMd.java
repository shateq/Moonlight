package shateq.java.moonlight.mods;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import shateq.java.moonlight.MoonlightBot;
import shateq.java.moonlight.util.Helpers;
import shateq.java.moonlight.util.Module;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetectionMd extends Module {
    public DetectionMd(Settings settings) {
        super(settings);
    }

    @Override
    public void start() {
        if (this.available()) {
            MoonlightBot.getJDA().getEventManager().register(this);
        }
    }

    @Override
    public void onMessageReceived(final @NotNull MessageReceivedEvent e) {
        final Message msg = e.getMessage();

        if (Helpers.checkURL(msg.getContentDisplay())) {
            final Site found = matching(msg.getContentDisplay());
            if (found == null) return;
            switch (found) {
                case YouTube ->
                    msg.addReaction(Emoji.fromCustom("yt", 838741674598465586L, false)); //msg.addReaction("yt:").queue();
                case Spotify -> msg.addReaction(Emoji.fromCustom("sp", 838741674591125504L, false)).queue();
                case Reddit -> msg.addReaction(Emoji.fromCustom("rd", 838741674628743209L, false)).queue();
                case Discord -> msg.addReaction(Emoji.fromCustom("dc", 838735948124651550L, false)).queue();
            }
        }
    }

    private @Nullable Site matching(final String link) {
        final Matcher yt = Site.YouTube.pattern.matcher(link);
        final Matcher sp = Site.Spotify.pattern.matcher(link);
        final Matcher rd = Site.Reddit.pattern.matcher(link);
        final Matcher dc = Site.Discord.pattern.matcher(link);

        if (yt.find()) {
            return Site.YouTube;
        }
        if (sp.find()) {
            return Site.Spotify;
        }
        if (rd.find()) {
            return Site.Reddit;
        }
        if (dc.find()) {
            return Site.Discord;
        }
        return null;
    }

    private enum Site {
        YouTube(Pattern.compile("(?:https?:\\/\\/)?(?:(?:www\\.?)?youtube\\.com)|(?:youtu\\.be(\\/.*)?)")),
        Spotify(Pattern.compile("(?:https?:\\/\\/)?(open.|play.)spotify.com")),
        Reddit(Pattern.compile("(?:https?:\\/\\/)?(?:www\\.)?(redd(it.com|.it))")),
        Discord(Pattern.compile("(?:https?:\\/\\/)?(discord(?:app)?\\.com)"));

        private final Pattern pattern;

        Site(final Pattern pattern) {
            this.pattern = pattern;
        }
    }
}
