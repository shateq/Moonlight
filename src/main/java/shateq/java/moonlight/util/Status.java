package shateq.java.moonlight.util;

public enum Status {
    ON("🟢"), OFF("🟡"), WAITING("🔴"), BUILT("🔵"), SPECIAL("🟠");

    public final String mark;

    Status(final String mark) {
        this.mark = mark;
    }
}