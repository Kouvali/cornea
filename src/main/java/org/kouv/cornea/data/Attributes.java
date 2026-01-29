package org.kouv.cornea.data;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;

public final class Attributes {
    private final Map<AttributeKey<?>, Object> map = new IdentityHashMap<>();

    @ApiStatus.Internal
    public Attributes() {
    }

    public boolean contains(AttributeKey<?> key) {
        Objects.requireNonNull(key);
        return map.containsKey(key);
    }

    @SuppressWarnings("unchecked")
    public <T> @Nullable T get(AttributeKey<T> key) {
        Objects.requireNonNull(key);
        return (T) map.get(key);
    }

    public <T> void set(AttributeKey<T> key, T value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        map.put(key, value);
    }

    public <T> void remove(AttributeKey<T> key) {
        Objects.requireNonNull(key);
        map.remove(key);
    }
}
