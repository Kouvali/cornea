package org.kouv.cornea.holders;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.kouv.cornea.events.Disposable;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public interface ElementHolderHook {
    Disposable cornea$addStartWatchingListener(Consumer<? super ServerPlayNetworkHandler> consumer);

    Disposable cornea$addStopWatchingListener(Consumer<? super ServerPlayNetworkHandler> consumer);

    Disposable cornea$addTickListener(Runnable runnable);
}
