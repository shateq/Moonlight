package shateq.moonlight.modules;

import shateq.moonlight.util.Identifier;

public class Fishing extends Module {
    public Fishing(Identifier id, Status status) {
        super(id, status);
    }

    private enum Fish {
        Fish("🐟"), Puffer("🐡"), Shark("🦈"), Exotic("🐠"), Crab("🦀"), Paper("🧻"), Ball("⚽"), Scroll("📜"), Boot("👞"), Nothing("❔");

        final String loot;

        Fish(final String loot) {
            this.loot = loot;
        }
    }
}
