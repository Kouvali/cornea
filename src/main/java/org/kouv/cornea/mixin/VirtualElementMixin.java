package org.kouv.cornea.mixin;

import eu.pb4.polymer.virtualentity.api.elements.VirtualElement;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
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
        },
        remap = false
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
    private boolean cornea$markedForRemoval = false;
    @Unique
    private double cornea$drag = 1.0;
    @Unique
    private double cornea$gravity = 0.0;
    @Unique
    private Vec3d cornea$velocity = Vec3d.ZERO;

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
    public boolean cornea$isMarkedForRemoval() {
        return cornea$markedForRemoval;
    }

    @Override
    public void cornea$setMarkedForRemoval(boolean marked) {
        cornea$markedForRemoval = marked;
    }

    @Override
    public double cornea$getDrag() {
        return cornea$drag;
    }

    @Override
    public void cornea$setDrag(double drag) {
        cornea$drag = drag;
    }

    @Override
    public double cornea$getGravity() {
        return cornea$gravity;
    }

    @Override
    public void cornea$setGravity(double gravity) {
        cornea$gravity = gravity;
    }

    @Override
    public Vec3d cornea$getVelocity() {
        return cornea$velocity;
    }

    @Override
    public void cornea$setVelocity(Vec3d velocity) {
        Objects.requireNonNull(velocity);
        cornea$velocity = velocity;
    }

    @Inject(
            method = "startWatching",
            at = @At(value = "TAIL")
    )
    private void cornea$invokeStartWatchingListeners(ServerPlayerEntity player, Consumer<Packet<ClientPlayPacketListener>> packetConsumer, CallbackInfo ci) {
        if (cornea$startWatchingListeners != null) {
            for (StartWatchingListener listener : cornea$startWatchingListeners) {
                listener.onStartWatching(player.networkHandler, packetConsumer);
            }
        }
    }

    @Inject(
            method = "stopWatching",
            at = @At(value = "TAIL")
    )
    private void cornea$invokeStopWatchingListeners(ServerPlayerEntity player, Consumer<Packet<ClientPlayPacketListener>> packetConsumer, CallbackInfo ci) {
        if (cornea$stopWatchingListeners != null) {
            for (StopWatchingListener listener : cornea$stopWatchingListeners) {
                listener.onStopWatching(player.networkHandler, packetConsumer);
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
        if (cornea$markedForRemoval) {
            ci.cancel();
            if (getHolder() != null) {
                getHolder().removeElement(this);
            }
        }
    }

    @Inject(
            method = "tick",
            at = @At(value = "HEAD")
    )
    private void cornea$tickPhysics(CallbackInfo ci) {
        if (Math.abs(cornea$drag - 1.0) > 1.0E-6) {
            cornea$velocity = cornea$velocity.multiply(cornea$drag);
        }

        if (Math.abs(cornea$gravity) > 1.0E-6) {
            cornea$velocity = cornea$velocity.subtract(0, cornea$gravity, 0);
        }

        if (cornea$velocity.lengthSquared() > 1E-6) {
            setOffset(getOffset().add(cornea$velocity));
        }
    }
}
