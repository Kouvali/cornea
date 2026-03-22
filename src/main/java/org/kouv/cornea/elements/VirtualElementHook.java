package org.kouv.cornea.elements;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.phys.Vec3;
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

    Vec3 cornea$getVelocity();

    void cornea$setVelocity(Vec3 velocity);

    @FunctionalInterface
    interface StartWatchingListener {
        void onStartWatching(ServerGamePacketListenerImpl connection, Consumer<? super Packet<ClientGamePacketListener>> packetConsumer);
    }

    @FunctionalInterface
    interface StopWatchingListener {
        void onStopWatching(ServerGamePacketListenerImpl connection, Consumer<? super Packet<ClientGamePacketListener>> packetConsumer);
    }

    @FunctionalInterface
    interface TickListener {
        void onTick();
    }
}
