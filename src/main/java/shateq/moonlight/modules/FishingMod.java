package shateq.moonlight.modules;

import shateq.moonlight.util.Identifier;

public class FishingMod extends Module {
    public FishingMod(Identifier id, Status status) {
        super(id, status);
        System.out.println("FISHING");
        System.out.println("FISHING");
        System.out.println("FISHING");
    }

    protected enum Fish {
        Fish("🐟"), Puffer("🐡"), Shark("🦈"), Exotic("🐠"), Crab("🦀"), Paper("🧻"), Ball("⚽"), Scroll("📜"), Boot("👞"), Nothing("❔");

        final String loot;

        Fish(final String loot) {
            this.loot = loot;
        }
    }
}
