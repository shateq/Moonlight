package shateq.moonlight.mod;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import shateq.moonlight.MoonlightBot;
import shateq.moonlight.dispatcher.GuildContext;
import shateq.moonlight.dispatcher.api.Category;
import shateq.moonlight.dispatcher.api.Command;
import shateq.moonlight.dispatcher.api.Order;
import shateq.moonlight.mod.api.Identifier;
import shateq.moonlight.mod.api.Module;
import shateq.moonlight.mod.api.ModuleStatus;

public class FishingMod extends Module {
    public FishingMod(Identifier id, ModuleStatus status) {
        super(id, status);
    }

    @Override
    public void init() {
        super.init();
        MoonlightBot.dispatcher().register(new FishingCmd());
    }

    enum Fish {
        Fish("๐"), Puffer("๐ก"), Shark("๐ฆ"),
        Exotic("๐ "), Crab("๐ฆ"), Paper("๐งป"),
        Ball("โฝ"), Scroll("๐"), Boot("๐"),
        Nothing("โ");

        final String loot;
        Fish(String loot) {
            this.loot = loot;
        }
    }

    @Order(value = "fish", group = Category.Fishing)
    static class FishingCmd implements Command {
        @Override
        public void execute(@NotNull GuildContext c) {
            throw new NotImplementedError();
        }
    }
}
