package org.kouv.cornea.data;

import java.util.Objects;

public final class AttributeKey<T> {
    private final String name;

    public AttributeKey(String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public String toString() {
        return "AttributeKey(" + name + ")";
    }
}
