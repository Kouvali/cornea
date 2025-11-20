package org.kouv.cornea.elements;

import net.minecraft.entity.Entity;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public interface AbstractElementHook {
    List<? extends StartWatchingListener> cornea$getStartWatchingListeners();

    void cornea$addStartWatchingListener(StartWatchingListener startWatchingListener);

    void cornea$removeStartWatchingListener(StartWatchingListener startWatchingListener);

    List<? extends StopWatchingListener> cornea$getStopWatchingListeners();

    void cornea$addStopWatchingListener(StopWatchingListener stopWatchingListener);

    void cornea$removeStopWatchingListener(StopWatchingListener stopWatchingListener);

    List<? extends TickListener> cornea$getTickListeners();

    void cornea$addTickListener(TickListener tickListener);

    void cornea$removeTickListener(TickListener tickListener);

    Vec3d cornea$getOffsetVelocity();

    void cornea$setOffsetVelocity(Vec3d offsetVelocity);

    @Nullable Entity cornea$getVelocityRef();

    void cornea$setVelocityRef(@Nullable Entity velocityRef);

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
