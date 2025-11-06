package org.kouv.cornea.mixin;

import eu.pb4.polymer.virtualentity.api.ElementHolder;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.kouv.cornea.holders.ElementHolderHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

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

    @Inject(method = "startWatching(Lnet/minecraft/server/network/ServerPlayNetworkHandler;)Z", at = @At(value = "RETURN"))
    private void cornea$startWatching(ServerPlayNetworkHandler player, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ()) {
            for (StartWatchingListener startWatchingListener : cornea$startWatchingListeners) {
                startWatchingListener.onStartWatching(player);
            }
        }
    }

    @Inject(method = "stopWatching(Lnet/minecraft/server/network/ServerPlayNetworkHandler;)Z", at = @At(value = "RETURN"))
    private void cornea$stopWatching(ServerPlayNetworkHandler player, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ()) {
            for (StopWatchingListener stopWatchingListener : cornea$stopWatchingListeners) {
                stopWatchingListener.onStopWatching(player);
            }
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Leu/pb4/polymer/virtualentity/api/ElementHolder;onTick()V"))
    private void cornea$tick(CallbackInfo ci) {
        for (TickListener tickListener : cornea$tickListeners) {
            tickListener.onTick();
        }
    }
}
