package org.kouv.cornea.holders;

import net.minecraft.server.network.ServerPlayNetworkHandler;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public interface ElementHolderHook {
    void cornea$addStartWatchingListener(Consumer<? super ServerPlayNetworkHandler> consumer);

    void cornea$addStopWatchingListener(Consumer<? super ServerPlayNetworkHandler> consumer);

    void cornea$addTickListener(Runnable runnable);
}
