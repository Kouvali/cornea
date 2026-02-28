package org.kouv.cornea.elements;

import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.math.Vec3d;
import org.kouv.cornea.data.Attributes;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public interface VirtualElementHook {
    Attributes cornea$getAttributes();

    void cornea$addStartWatchingListener(StartWatchingListener listener);

    void cornea$removeStartWatchingListener(StartWatchingListener listener);

    void cornea$addStopWatchingListener(StopWatchingListener listener);

    void cornea$removeStopWatchingListener(StopWatchingListener listener);

    void cornea$addTickListener(TickListener listener);

    void cornea$removeTickListener(TickListener listener);

    int cornea$getRemovalDelay();

    void cornea$setRemovalDelay(int removalDelay);

    double cornea$getDrag();

    void cornea$setDrag(double drag);

    double cornea$getGravity();

    void cornea$setGravity(double gravity);

    Vec3d cornea$getVelocity();

    void cornea$setVelocity(Vec3d velocity);

    @FunctionalInterface
    interface StartWatchingListener {
        void onStartWatching(ServerPlayNetworkHandler networkHandler, Consumer<? super Packet<ClientPlayPacketListener>> packetConsumer);
    }

    @FunctionalInterface
    interface StopWatchingListener {
        void onStopWatching(ServerPlayNetworkHandler networkHandler, Consumer<? super Packet<ClientPlayPacketListener>> packetConsumer);
    }

    @FunctionalInterface
    interface TickListener {
        void onTick();
    }
}
