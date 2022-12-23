package shateq.moonlight.dispatcher.api;

/**
 * Command drawers
 */
public enum Category {
    General("Generalne"),
    Music("Muzyka"),
    Slash("Slash*"), //extend
    Fishing("Rybactwo") ;

    public final String title;
    Category(final String title) {
        this.title = title;
    }
}
