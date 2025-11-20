package org.kouv.cornea.mixin;

import eu.pb4.polymer.virtualentity.api.elements.AbstractElement;
import eu.pb4.polymer.virtualentity.api.elements.VirtualElement;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
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
    private Vec3d cornea$offsetVelocity = Vec3d.ZERO;
    @Unique
    private @Nullable Entity cornea$velocityRef;

    @Override
    public List<? extends StartWatchingListener> cornea$getStartWatchingListeners() {
        return cornea$startWatchingListeners;
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
    public List<? extends StopWatchingListener> cornea$getStopWatchingListeners() {
        return cornea$stopWatchingListeners;
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
    public List<? extends TickListener> cornea$getTickListeners() {
        return cornea$tickListeners;
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
    public Vec3d cornea$getOffsetVelocity() {
        return cornea$offsetVelocity;
    }

    @Override
    public void cornea$setOffsetVelocity(Vec3d offsetVelocity) {
        Objects.requireNonNull(offsetVelocity);
        cornea$offsetVelocity = offsetVelocity;
    }

    @Override
    public @Nullable Entity cornea$getVelocityRef() {
        return cornea$velocityRef;
    }

    @Override
    public void cornea$setVelocityRef(@Nullable Entity velocityRef) {
        Objects.requireNonNull(velocityRef);
        cornea$velocityRef = velocityRef;
    }
}
