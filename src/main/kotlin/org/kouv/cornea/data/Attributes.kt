package org.kouv.cornea.data

import java.util.*

public class AttributeKey<T>(
    public val name: String
)

public class Attributes internal constructor() {
    private val map: MutableMap<AttributeKey<*>, Any> = IdentityHashMap()

    public operator fun contains(key: AttributeKey<*>): Boolean {
        return key in map
    }

    @Suppress("UNCHECKED_CAST")
    public operator fun <T : Any> get(key: AttributeKey<T>): T? {
        return map[key] as? T
    }

    public operator fun <T : Any> set(key: AttributeKey<T>, value: T) {
        map[key] = value
    }

    public fun <T : Any> remove(key: AttributeKey<T>) {
        map.remove(key)
    }
}