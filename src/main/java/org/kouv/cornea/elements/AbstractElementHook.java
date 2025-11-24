package org.kouv.cornea.elements;

import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public interface AbstractElementHook {
    void cornea$triggerStartWatchingListeners(ServerPlayerEntity player, Consumer<Packet<ClientPlayPacketListener>> packetConsumer);

    void cornea$addStartWatchingListener(StartWatchingListener startWatchingListener);

    void cornea$removeStartWatchingListener(StartWatchingListener startWatchingListener);

    void cornea$triggerStopWatchingListeners(ServerPlayerEntity player, Consumer<Packet<ClientPlayPacketListener>> packetConsumer);

    void cornea$addStopWatchingListener(StopWatchingListener stopWatchingListener);

    void cornea$removeStopWatchingListener(StopWatchingListener stopWatchingListener);

    void cornea$triggerTickListeners();

    void cornea$addTickListener(TickListener tickListener);

    void cornea$removeTickListener(TickListener tickListener);

    double cornea$getOffsetGravity();

    void cornea$setOffsetGravity(double offsetGravity);

    Vec3d cornea$getOffsetVelocity();

    void cornea$setOffsetVelocity(Vec3d offsetVelocity);

    @FunctionalInterface
    interface StartWatchingListener {
        void onStartWatching(ServerPlayerEntity player, Consumer<Packet<ClientPlayPacketListener>> packetConsumer);
    }

    @FunctionalInterface
    interface StopWatchingListener {
        void onStopWatching(ServerPlayerEntity player, Consumer<Packet<ClientPlayPacketListener>> packetConsumer);
    }

    @FunctionalInterface
    interface TickListener {
        void onTick();
    }
}
