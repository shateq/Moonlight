package shateq.moonlight.mod;

import net.dv8tion.jda.api.events.GenericEvent;
import shateq.moonlight.MoonlightBot;
import shateq.moonlight.util.Identifier;

public class FishingMod extends Module {
    public FishingMod(Identifier id, Status status) {
        super(id, status);
        System.out.println("FISHING");
        System.out.println("FISHING");
        System.out.println("FISHING");
    }

    @Override
    public void onGenericEvent(GenericEvent event) {
        System.out.println("generic event");
    }

    @Override
    public void init() {
        super.init();
        MoonlightBot.jda().addEventListener(this);
    }

    enum Fish {
        Fish("🐟"), Puffer("🐡"), Shark("🦈"), Exotic("🐠"), Crab("🦀"), Paper("🧻"), Ball("⚽"), Scroll("📜"), Boot("👞"), Nothing("❔");

        final String loot;

        Fish(String loot) {
            this.loot = loot;
        }
    }
}
