package org.kouv.cornea.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.elements.VirtualElement;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.math.Vec3d;
import org.kouv.cornea.elements.AbstractElementHook;
import org.kouv.cornea.holders.ElementHolderHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@Mixin(value = ElementHolder.class, remap = false)
public abstract class ElementHolderMixin implements ElementHolderHook {
    @Unique
    private final List<StartWatchingListener> cornea$startWatchingListeners = new CopyOnWriteArrayList<>();
    @Unique
    private final List<StopWatchingListener> cornea$stopWatchingListeners = new CopyOnWriteArrayList<>();
    @Unique
    private final List<TickListener> cornea$tickListeners = new CopyOnWriteArrayList<>();

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
    public void cornea$addTickListener(TickListener tickListener) {
        Objects.requireNonNull(tickListener);
        cornea$tickListeners.add(tickListener);
    }

    @Override
    public void cornea$removeTickListener(TickListener tickListener) {
        Objects.requireNonNull(tickListener);
        cornea$tickListeners.remove(tickListener);
    }

    @Inject(
            method = "startWatching(Lnet/minecraft/server/network/ServerPlayNetworkHandler;)Z",
            at = @At(value = "RETURN")
    )
    private void cornea$invokeStartWatchingListeners(ServerPlayNetworkHandler player, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ()) {
            for (StartWatchingListener startWatchingListener : cornea$startWatchingListeners) {
                startWatchingListener.onStartWatching(player);
            }
        }
    }

    @Inject(
            method = "startWatching(Lnet/minecraft/server/network/ServerPlayNetworkHandler;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Leu/pb4/polymer/virtualentity/api/elements/VirtualElement;startWatching(Lnet/minecraft/server/network/ServerPlayerEntity;Ljava/util/function/Consumer;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void cornea$invokeElementStartWatchingListeners(ServerPlayNetworkHandler player, CallbackInfoReturnable<Boolean> cir, @Local VirtualElement element, @Local ArrayList<Packet<? super ClientPlayPacketListener>> packets) {
        if (element instanceof AbstractElementHook hook) {
            for (AbstractElementHook.StartWatchingListener startWatchingListener : hook.cornea$getStartWatchingListeners()) {
                startWatchingListener.onStartWatching(player.getPlayer(), packets::add);
            }
        }
    }

    @Inject(
            method = "stopWatching(Lnet/minecraft/server/network/ServerPlayNetworkHandler;)Z",
            at = @At(value = "RETURN")
    )
    private void cornea$invokeStopWatchingListeners(ServerPlayNetworkHandler player, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ()) {
            for (StopWatchingListener stopWatchingListener : cornea$stopWatchingListeners) {
                stopWatchingListener.onStopWatching(player);
            }
        }
    }

    @Inject(
            method = "stopWatching(Lnet/minecraft/server/network/ServerPlayNetworkHandler;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Leu/pb4/polymer/virtualentity/api/elements/VirtualElement;stopWatching(Lnet/minecraft/server/network/ServerPlayerEntity;Ljava/util/function/Consumer;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void cornea$invokeElementStopWatchingListeners(ServerPlayNetworkHandler player, CallbackInfoReturnable<Boolean> cir, @Local VirtualElement element, @Local Consumer<Packet<ClientPlayPacketListener>> packetConsumer) {
        if (element instanceof AbstractElementHook hook) {
            for (AbstractElementHook.StopWatchingListener stopWatchingListener : hook.cornea$getStopWatchingListeners()) {
                stopWatchingListener.onStopWatching(player.getPlayer(), packetConsumer);
            }
        }
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Leu/pb4/polymer/virtualentity/api/ElementHolder;onTick()V",
                    shift = At.Shift.AFTER
            )
    )
    private void cornea$invokeTickListeners(CallbackInfo ci) {
        for (TickListener tickListener : cornea$tickListeners) {
            tickListener.onTick();
        }
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Leu/pb4/polymer/virtualentity/api/elements/VirtualElement;tick()V",
                    shift = At.Shift.AFTER
            )
    )
    private void cornea$invokeElementTickListeners(CallbackInfo ci, @Local VirtualElement element) {
        if (element instanceof AbstractElementHook hook) {
            for (AbstractElementHook.TickListener tickListener : hook.cornea$getTickListeners()) {
                tickListener.onTick();
            }
        }
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Leu/pb4/polymer/virtualentity/api/elements/VirtualElement;tick()V"
            )
    )
    private void cornea$applyElementOffsetPhysics(CallbackInfo ci, @Local VirtualElement element) {
        if (element instanceof AbstractElementHook hook) {
            double offsetGravity = hook.cornea$getOffsetGravity();
            if (Math.abs(offsetGravity) > 1e-6) {
                hook.cornea$setOffsetVelocity(hook.cornea$getOffsetVelocity().subtract(0, offsetGravity, 0));
            }

            Vec3d offsetVelocity = hook.cornea$getOffsetVelocity();
            if (offsetVelocity.lengthSquared() > 1e-6) {
                element.setOffset(element.getOffset().add(offsetVelocity));
            }
        }
    }
}
