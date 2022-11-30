package shateq.java.moonlight.mods;

import shateq.java.moonlight.util.Module;

public class BoostMd extends Module {

    public BoostMd(Settings settings) {
        super(settings);
    }

    public void start() {
        System.out.println(this.getId());
    }
}
