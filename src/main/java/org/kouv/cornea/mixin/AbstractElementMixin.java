package org.kouv.cornea.mixin;

import eu.pb4.polymer.virtualentity.api.elements.AbstractElement;
import org.kouv.cornea.elements.AbstractElementHook;
import org.kouv.cornea.events.Disposable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@Mixin(value = AbstractElement.class, remap = false)
public abstract class AbstractElementMixin implements AbstractElementHook {
    @Unique
    private final List<TickListener> cornea$tickListeners = new CopyOnWriteArrayList<>();

    @Override
    public List<? extends TickListener> cornea$getTickListeners() {
        return Collections.unmodifiableList(cornea$tickListeners);
    }

    @Override
    public Disposable cornea$addTickListener(TickListener tickListener) {
        Objects.requireNonNull(tickListener);
        cornea$tickListeners.add(tickListener);
        return () -> cornea$tickListeners.remove(tickListener);
    }

    @Override
    public void cornea$removeTickListener(TickListener tickListener) {
        Objects.requireNonNull(tickListener);
        cornea$tickListeners.remove(tickListener);
    }
}
