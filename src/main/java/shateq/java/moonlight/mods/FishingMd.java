package shateq.java.moonlight.mods;

import shateq.java.moonlight.MoonlightBot;
import shateq.java.moonlight.util.Module;

import java.util.concurrent.ThreadLocalRandom;

public class FishingMd extends Module {
    public FishingMd(Settings settings) {
        super(settings);
    }

    @Override
    public void start() {
        if (this.available()) {
            MoonlightBot.getJDA().getEventManager().register(this);
        }
    }

    private enum Fishing {
        Fish("🐟"), Puffer("🐡"), Shark("🦈"), Exotic("🐠"), Crab("🦀"), Paper("🧻"), Ball("⚽"), Scroll("📜"), Boot("👞"), Nothing("❔");

        final String loot;

        Fishing(final String loot) {
            this.loot = loot;
        }

        static Fishing selectRandom() {
            return values()[ThreadLocalRandom.current().nextInt(values().length)];
        }

    }
}
