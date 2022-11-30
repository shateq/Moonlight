package shateq.java.moonlight.util;

public enum Category {
    Blank(""), Music("Muzyka"), Games("Gry"), Akinator("Akinator");

    public final String title;

    Category(final String title) {
        this.title = title;
    }
}