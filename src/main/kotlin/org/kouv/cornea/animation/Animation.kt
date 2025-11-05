package org.kouv.cornea.animation

public class Animation<out T> internal constructor(
    public val frames: List<Frame<T>>
) {
    public val totalDuration: Int = frames.sumOf { frame -> frame.duration }
}

public class Frame<out T>(
    public val value: T,
    public val duration: Int
)

public fun <T> Animation<T>.valueSequence(): Sequence<T> = sequence {
    for (frame in frames) {
        repeat(frame.duration) {
            yield(frame.value)
        }
    }
}

public fun <T> Animation<T>.valueIterator(): Iterator<T> = valueSequence().iterator()

public class AnimationBuilder<T> @PublishedApi internal constructor() {
    private val frames: MutableList<Frame<T>> = mutableListOf()

    public fun add(frame: Frame<T>) {
        frames.add(frame)
    }

    public fun add(value: T, duration: Int = 1) {
        add(Frame(value, duration))
    }

    public fun addAll(frames: Collection<Frame<T>>) {
        this.frames.addAll(frames)
    }

    public fun addAll(values: Collection<T>, duration: Int = 1) {
        values.forEach { value -> add(value, duration) }
    }

    @PublishedApi
    internal fun build(): Animation<T> = Animation(frames)
}

public inline fun <T> animation(builderAction: AnimationBuilder<T>.() -> Unit): Animation<T> =
    AnimationBuilder<T>().apply(builderAction).build()