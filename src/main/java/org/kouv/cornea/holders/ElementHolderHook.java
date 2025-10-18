package org.kouv.cornea.holders;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.kouv.cornea.events.Disposable;

@SuppressWarnings("unused")
public interface ElementHolderHook {
    Disposable cornea$addStartWatchingListener(StartWatchingListener startWatchingListener);

    Disposable cornea$addStopWatchingListener(StopWatchingListener stopWatchingListener);

    Disposable cornea$addTickListener(TickListener tickListener);

    @FunctionalInterface
    interface StartWatchingListener {
        void onStartWatching(Disposable disposable, ServerPlayNetworkHandler player);
    }

    @FunctionalInterface
    interface StopWatchingListener {
        void onStopWatching(Disposable disposable, ServerPlayNetworkHandler player);
    }

    @FunctionalInterface
    interface TickListener {
        void onTick(Disposable disposable);
    }
}
