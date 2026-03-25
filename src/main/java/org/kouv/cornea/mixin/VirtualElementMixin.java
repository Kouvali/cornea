package org.kouv.cornea.mixin;

import eu.pb4.polymer.virtualentity.api.elements.VirtualElement;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.kouv.cornea.data.Attributes;
import org.kouv.cornea.elements.VirtualElementHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@Mixin(
        targets = {
                "eu.pb4.polymer.virtualentity.api.elements.EntityElement",
                "eu.pb4.polymer.virtualentity.api.elements.GenericEntityElement"
        }
)
public abstract class VirtualElementMixin implements VirtualElement, VirtualElementHook {
    @Unique
    private @Nullable Attributes cornea$attributes = null;
    @Unique
    private @Nullable List<StartWatchingListener> cornea$startWatchingListeners = null;
    @Unique
    private @Nullable List<StopWatchingListener> cornea$stopWatchingListeners = null;
    @Unique
    private @Nullable List<TickListener> cornea$tickListeners = null;
    @Unique
    private int cornea$removalDelay = -1;
    @Unique
    private double cornea$offsetDrag = 1.0;
    @Unique
    private double cornea$offsetGravity = 0.0;
    @Unique
    private Vec3 cornea$offsetVelocity = Vec3.ZERO;

    @Override
    public Attributes cornea$getAttributes() {
        if (cornea$attributes == null) {
            cornea$attributes = new Attributes();
        }

        return cornea$attributes;
    }

    @Override
    public void cornea$addStartWatchingListener(StartWatchingListener listener) {
        Objects.requireNonNull(listener);
        if (cornea$startWatchingListeners == null) {
            cornea$startWatchingListeners = new CopyOnWriteArrayList<>();
        }

        cornea$startWatchingListeners.add(listener);
    }

    @Override
    public void cornea$removeStartWatchingListener(StartWatchingListener listener) {
        Objects.requireNonNull(listener);
        if (cornea$startWatchingListeners != null) {
            cornea$startWatchingListeners.remove(listener);
        }
    }

    @Override
    public void cornea$addStopWatchingListener(StopWatchingListener listener) {
        Objects.requireNonNull(listener);
        if (cornea$stopWatchingListeners == null) {
            cornea$stopWatchingListeners = new CopyOnWriteArrayList<>();
        }

        cornea$stopWatchingListeners.add(listener);
    }

    @Override
    public void cornea$removeStopWatchingListener(StopWatchingListener listener) {
        Objects.requireNonNull(listener);
        if (cornea$stopWatchingListeners != null) {
            cornea$stopWatchingListeners.remove(listener);
        }
    }

    @Override
    public void cornea$addTickListener(TickListener listener) {
        Objects.requireNonNull(listener);
        if (cornea$tickListeners == null) {
            cornea$tickListeners = new CopyOnWriteArrayList<>();
        }

        cornea$tickListeners.add(listener);
    }

    @Override
    public void cornea$removeTickListener(TickListener listener) {
        Objects.requireNonNull(listener);
        if (cornea$tickListeners != null) {
            cornea$tickListeners.remove(listener);
        }
    }

    @Override
    public int cornea$getRemovalDelay() {
        return cornea$removalDelay;
    }

    @Override
    public void cornea$setRemovalDelay(int removalDelay) {
        cornea$removalDelay = removalDelay;
    }

    @Override
    public double cornea$getOffsetDrag() {
        return cornea$offsetDrag;
    }

    @Override
    public void cornea$setOffsetDrag(double offsetDrag) {
        cornea$offsetDrag = offsetDrag;
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
    public Vec3 cornea$getOffsetVelocity() {
        return cornea$offsetVelocity;
    }

    @Override
    public void cornea$setOffsetVelocity(Vec3 offsetVelocity) {
        Objects.requireNonNull(offsetVelocity);
        cornea$offsetVelocity = offsetVelocity;
    }

    @Inject(
            method = "startWatching",
            at = @At(value = "TAIL")
    )
    private void cornea$invokeStartWatchingListeners(ServerPlayer player, Consumer<Packet<ClientGamePacketListener>> packetConsumer, CallbackInfo ci) {
        if (cornea$startWatchingListeners != null) {
            for (StartWatchingListener listener : cornea$startWatchingListeners) {
                listener.onStartWatching(player.connection, packetConsumer);
            }
        }
    }

    @Inject(
            method = "stopWatching",
            at = @At(value = "TAIL")
    )
    private void cornea$invokeStopWatchingListeners(ServerPlayer player, Consumer<Packet<ClientGamePacketListener>> packetConsumer, CallbackInfo ci) {
        if (cornea$stopWatchingListeners != null) {
            for (StopWatchingListener listener : cornea$stopWatchingListeners) {
                listener.onStopWatching(player.connection, packetConsumer);
            }
        }
    }

    @Inject(
            method = "tick",
            at = @At(value = "TAIL")
    )
    private void cornea$invokeTickListeners(CallbackInfo ci) {
        if (cornea$tickListeners != null) {
            for (TickListener listener : cornea$tickListeners) {
                listener.onTick();
            }
        }
    }

    @Inject(
            method = "tick",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void cornea$processPendingRemoval(CallbackInfo ci) {
        if (cornea$removalDelay >= 0) {
            if (cornea$removalDelay == 0) {
                ci.cancel();
                if (getHolder() != null) {
                    getHolder().removeElement(this);
                }
            }

            cornea$removalDelay--;
        }
    }

    @Inject(
            method = "tick",
            at = @At(value = "HEAD")
    )
    private void cornea$tickPhysics(CallbackInfo ci) {
        if (Math.abs(cornea$offsetDrag - 1.0) > 1.0E-6) {
            cornea$offsetVelocity = cornea$offsetVelocity.scale(cornea$offsetDrag);
        }

        if (Math.abs(cornea$offsetGravity) > 1.0E-6) {
            cornea$offsetVelocity = cornea$offsetVelocity.subtract(0, cornea$offsetGravity, 0);
        }

        if (cornea$offsetVelocity.lengthSqr() > 1E-6) {
            setOffset(getOffset().add(cornea$offsetVelocity));
        }
    }
}
