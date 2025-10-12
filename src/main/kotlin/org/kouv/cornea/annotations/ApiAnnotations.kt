package org.kouv.cornea.annotations

@RequiresOptIn(
    level = RequiresOptIn.Level.WARNING,
    message = "This API is experimental in polymer and may change in future versions"
)
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.TYPEALIAS
)
public annotation class ExperimentalPolymerApi

@RequiresOptIn(
    level = RequiresOptIn.Level.ERROR,
    message = "This API is internal to polymer and not intended for public use"
)
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.TYPEALIAS
)
public annotation class InternalPolymerApi