package org.kouv.cornea.elements;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.math.Vec3d;

@SuppressWarnings("unused")
public interface AbstractElementHook {
    void cornea$triggerStartWatchingListeners(ServerPlayNetworkHandler networkHandler);

    void cornea$addStartWatchingListener(StartWatchingListener listener);

    void cornea$removeStartWatchingListener(StartWatchingListener listener);

    void cornea$triggerStopWatchingListeners(ServerPlayNetworkHandler networkHandler);

    void cornea$addStopWatchingListener(StopWatchingListener listener);

    void cornea$removeStopWatchingListener(StopWatchingListener listener);

    void cornea$triggerTickListeners();

    void cornea$addTickListener(TickListener listener);

    void cornea$removeTickListener(TickListener listener);

    double cornea$getOffsetDrag();

    void cornea$setOffsetDrag(double drag);

    double cornea$getOffsetGravity();

    void cornea$setOffsetGravity(double gravity);

    Vec3d cornea$getOffsetVelocity();

    void cornea$setOffsetVelocity(Vec3d velocity);

    @FunctionalInterface
    interface StartWatchingListener {
        void onStartWatching(ServerPlayNetworkHandler networkHandler);
    }

    @FunctionalInterface
    interface StopWatchingListener {
        void onStopWatching(ServerPlayNetworkHandler networkHandler);
    }

    @FunctionalInterface
    interface TickListener {
        void onTick();
    }
}
