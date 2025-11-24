package org.kouv.cornea.mixin;

import eu.pb4.polymer.virtualentity.api.elements.AbstractElement;
import eu.pb4.polymer.virtualentity.api.elements.VirtualElement;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.kouv.cornea.elements.AbstractElementHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@Mixin(value = AbstractElement.class, remap = false)
public abstract class AbstractElementMixin implements AbstractElementHook, VirtualElement {
    @Unique
    private final List<StartWatchingListener> cornea$startWatchingListeners = new CopyOnWriteArrayList<>();
    @Unique
    private final List<StopWatchingListener> cornea$stopWatchingListeners = new CopyOnWriteArrayList<>();
    @Unique
    private final List<TickListener> cornea$tickListeners = new CopyOnWriteArrayList<>();
    @Unique
    private double cornea$offsetGravity = 0.0;
    @Unique
    private Vec3d cornea$offsetVelocity = Vec3d.ZERO;

    @Override
    public void cornea$triggerStartWatchingListeners(ServerPlayerEntity player, Consumer<Packet<ClientPlayPacketListener>> packetConsumer) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(packetConsumer);
        cornea$startWatchingListeners.forEach(startWatchingListener -> startWatchingListener.onStartWatching(player, packetConsumer));
    }

    @Override
    public void cornea$addStartWatchingListener(StartWatchingListener startWatchingListener) {
        Objects.requireNonNull(startWatchingListener);
        cornea$startWatchingListeners.add(startWatchingListener);
    }

    @Override
    public void cornea$removeStartWatchingListener(StartWatchingListener startWatchingListener) {
        Objects.requireNonNull(startWatchingListener);
        cornea$startWatchingListeners.remove(startWatchingListener);
    }

    @Override
    public void cornea$triggerStopWatchingListeners(ServerPlayerEntity player, Consumer<Packet<ClientPlayPacketListener>> packetConsumer) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(packetConsumer);
        cornea$stopWatchingListeners.forEach(stopWatchingListener -> stopWatchingListener.onStopWatching(player, packetConsumer));
    }

    @Override
    public void cornea$addStopWatchingListener(StopWatchingListener stopWatchingListener) {
        Objects.requireNonNull(stopWatchingListener);
        cornea$stopWatchingListeners.add(stopWatchingListener);
    }

    @Override
    public void cornea$removeStopWatchingListener(StopWatchingListener stopWatchingListener) {
        Objects.requireNonNull(stopWatchingListener);
        cornea$stopWatchingListeners.remove(stopWatchingListener);
    }

    @Override
    public void cornea$triggerTickListeners() {
        cornea$tickListeners.forEach(TickListener::onTick);
    }

    @Override
    public void cornea$addTickListener(TickListener tickListener) {
        Objects.requireNonNull(tickListener);
        cornea$tickListeners.add(tickListener);
    }

    @Override
    public void cornea$removeTickListener(TickListener tickListener) {
        Objects.requireNonNull(tickListener);
        cornea$tickListeners.remove(tickListener);
    }

    @Override
    public double cornea$getOffsetGravity() {
        return cornea$offsetGravity;
    }

    @Override
    public void cornea$setOffsetGravity(double offsetGravity) {
        cornea$offsetGravity = offsetGravity;
    }

    @Override
    public Vec3d cornea$getOffsetVelocity() {
        return cornea$offsetVelocity;
    }

    @Override
    public void cornea$setOffsetVelocity(Vec3d offsetVelocity) {
        Objects.requireNonNull(offsetVelocity);
        cornea$offsetVelocity = offsetVelocity;
    }
}
