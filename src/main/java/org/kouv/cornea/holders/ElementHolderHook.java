package org.kouv.cornea.holders;

import net.minecraft.server.network.ServerPlayNetworkHandler;

@SuppressWarnings("unused")
public interface ElementHolderHook {
    void cornea$addStartWatchingListener(StartWatchingListener startWatchingListener);

    void cornea$removeStartWatchingListener(StartWatchingListener startWatchingListener);

    void cornea$addStopWatchingListener(StopWatchingListener stopWatchingListener);

    void cornea$removeStopWatchingListener(StopWatchingListener stopWatchingListener);

    void cornea$addTickListener(TickListener tickListener);

    void cornea$removeTickListener(TickListener tickListener);

    @FunctionalInterface
    interface StartWatchingListener {
        void onStartWatching(ServerPlayNetworkHandler player);
    }

    @FunctionalInterface
    interface StopWatchingListener {
        void onStopWatching(ServerPlayNetworkHandler player);
    }

    @FunctionalInterface
    interface TickListener {
        void onTick();
    }
}
