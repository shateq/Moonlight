package shateq.moonlight.dispatcher.api

/**
 * Command meta data
 */
@Target(AnnotationTarget.CLASS)
@MustBeDocumented
annotation class Order(val value: String, val group: Category = Category.General) {
    @Target(AnnotationTarget.CLASS)
    annotation class Aliases(val value: Array<String>)

    @Target(AnnotationTarget.CLASS)
    annotation class Explanation(val value: String)

    @Target(AnnotationTarget.CLASS)
    annotation class Example(val value: String)

    @Target(AnnotationTarget.CLASS)
    annotation class Hidden
}
