package org.kouv.cornea.elements;

import org.kouv.cornea.events.Disposable;

import java.util.List;

@SuppressWarnings("unused")
public interface AbstractElementHook {
    List<? extends TickListener> cornea$getTickListeners();

    Disposable cornea$addTickListener(TickListener tickListener);

    void cornea$removeTickListener(TickListener tickListener);

    @FunctionalInterface
    interface TickListener {
        void onTick(Disposable disposable);
    }
}
