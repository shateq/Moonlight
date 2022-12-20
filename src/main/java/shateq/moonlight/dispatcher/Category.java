package shateq.moonlight.dispatcher;

/**
 * Command drawers
 */
public enum Category {
    General("Generalne"), Slash("Slash"),
    Music("Muzyka"), Fun("Gry");

    public final String title;

    Category(final String title) {
        this.title = title;
    }
}
