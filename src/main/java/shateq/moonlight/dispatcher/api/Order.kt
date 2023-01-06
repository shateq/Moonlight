package shateq.moonlight.dispatcher.api

/**
 * Command metadata, used to make Command class viable source of feedback and action
 */
@Target(AnnotationTarget.CLASS)
@MustBeDocumented
annotation class Order(val value: String, val group: Category = Category.General, val note: String = "Brak danych.") {
    /**
     * Other associated names.
     */
    @Target(AnnotationTarget.CLASS)
    annotation class Aliases(val value: Array<String>)

    /**
     * Example usage of command as a sharing context.
     */
    @Target(AnnotationTarget.CLASS)
    annotation class Example(val value: String)

    /**
     * Filtering concepts.
     */
    @Target(AnnotationTarget.CLASS)
    annotation class Hidden

    /*@Target(AnnotationTarget.TYPE) // IMAGINE IF THIS WAS POSSIBLE
    annotation class Slash(val value: (Unit) -> SlashCommandData)*/
}
