package shateq.moonlight.dispatcher

/**
 * Command meta data
 */
@Target(AnnotationTarget.TYPE)
@MustBeDocumented
annotation class Order(val value: String) {
    
    @Target(AnnotationTarget.TYPE)
    annotation class Rank(val value: Category = Category.General)

    @Target(AnnotationTarget.TYPE)
    annotation class Aliases(val value: Array<String>)

    @Target(AnnotationTarget.TYPE)
    annotation class Explanation(val value: String)

    @Target(AnnotationTarget.TYPE)
    annotation class Example(val value: String)

    @Target(AnnotationTarget.TYPE)
    annotation class Hidden
}
