package org.kouv.cornea.mixin;

import eu.pb4.polymer.virtualentity.api.elements.AbstractElement;
import eu.pb4.polymer.virtualentity.api.elements.VirtualElement;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.math.Vec3d;
import org.kouv.cornea.elements.AbstractElementHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@Mixin(value = AbstractElement.class, remap = false)
public abstract class AbstractElementMixin implements AbstractElementHook, VirtualElement {
    @Unique
    private final List<StartWatchingListener> cornea$startWatchingListeners = new CopyOnWriteArrayList<>();
    @Unique
    private final List<StopWatchingListener> cornea$stopWatchingListeners = new CopyOnWriteArrayList<>();
    @Unique
    private final List<TickListener> cornea$tickListeners = new CopyOnWriteArrayList<>();
    @Unique
    private double cornea$offsetDrag = 1.0;
    @Unique
    private double cornea$offsetGravity = 0.0;
    @Unique
    private Vec3d cornea$offsetVelocity = Vec3d.ZERO;

    @Override
    public void cornea$triggerStartWatchingListeners(ServerPlayNetworkHandler networkHandler) {
        Objects.requireNonNull(networkHandler);
        cornea$startWatchingListeners.forEach(listener -> listener.onStartWatching(networkHandler));
    }

    @Override
    public void cornea$addStartWatchingListener(StartWatchingListener listener) {
        Objects.requireNonNull(listener);
        cornea$startWatchingListeners.add(listener);
    }

    @Override
    public void cornea$removeStartWatchingListener(StartWatchingListener listener) {
        Objects.requireNonNull(listener);
        cornea$startWatchingListeners.remove(listener);
    }

    @Override
    public void cornea$triggerStopWatchingListeners(ServerPlayNetworkHandler networkHandler) {
        Objects.requireNonNull(networkHandler);
        cornea$stopWatchingListeners.forEach(listener -> listener.onStopWatching(networkHandler));
    }

    @Override
    public void cornea$addStopWatchingListener(StopWatchingListener listener) {
        Objects.requireNonNull(listener);
        cornea$stopWatchingListeners.add(listener);
    }

    @Override
    public void cornea$removeStopWatchingListener(StopWatchingListener listener) {
        Objects.requireNonNull(listener);
        cornea$stopWatchingListeners.remove(listener);
    }

    @Override
    public void cornea$triggerTickListeners() {
        cornea$tickListeners.forEach(TickListener::onTick);
    }

    @Override
    public void cornea$addTickListener(TickListener listener) {
        Objects.requireNonNull(listener);
        cornea$tickListeners.add(listener);
    }

    @Override
    public void cornea$removeTickListener(TickListener tickListener) {
        Objects.requireNonNull(tickListener);
        cornea$tickListeners.remove(tickListener);
    }

    @Override
    public double cornea$getOffsetDrag() {
        return cornea$offsetDrag;
    }

    @Override
    public void cornea$setOffsetDrag(double drag) {
        cornea$offsetDrag = drag;
    }

    @Override
    public double cornea$getOffsetGravity() {
        return cornea$offsetGravity;
    }

    @Override
    public void cornea$setOffsetGravity(double gravity) {
        cornea$offsetGravity = gravity;
    }

    @Override
    public Vec3d cornea$getOffsetVelocity() {
        return cornea$offsetVelocity;
    }

    @Override
    public void cornea$setOffsetVelocity(Vec3d velocity) {
        Objects.requireNonNull(velocity);
        cornea$offsetVelocity = velocity;
    }
}
