package org.kouv.cornea.elements;

import org.kouv.cornea.events.Disposable;

import java.util.List;

@SuppressWarnings("unused")
public interface AbstractElementHook {
    List<? extends Runnable> cornea$getTickListeners();

    Disposable cornea$addTickListener(Runnable runnable);
}
