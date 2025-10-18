package org.kouv.cornea.mixin;

import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.elements.VirtualElement;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.kouv.cornea.elements.AbstractElementHook;
import org.kouv.cornea.events.Disposable;
import org.kouv.cornea.holders.ElementHolderHook;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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

    @Shadow
    @Final
    private List<VirtualElement> elements;

    @Override
    public Disposable cornea$addStartWatchingListener(StartWatchingListener startWatchingListener) {
        Objects.requireNonNull(startWatchingListener);
        cornea$startWatchingListeners.add(startWatchingListener);
        return () -> cornea$startWatchingListeners.remove(startWatchingListener);
    }

    @Override
    public Disposable cornea$addStopWatchingListener(StopWatchingListener stopWatchingListener) {
        Objects.requireNonNull(stopWatchingListener);
        cornea$stopWatchingListeners.add(stopWatchingListener);
        return () -> cornea$stopWatchingListeners.remove(stopWatchingListener);
    }

    @Override
    public Disposable cornea$addTickListener(TickListener tickListener) {
        Objects.requireNonNull(tickListener);
        cornea$tickListeners.add(tickListener);
        return () -> cornea$tickListeners.remove(tickListener);
    }

    @Inject(method = "startWatching(Lnet/minecraft/server/network/ServerPlayNetworkHandler;)Z", at = @At(value = "RETURN"))
    private void cornea$startWatching(ServerPlayNetworkHandler player, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ()) {
            for (StartWatchingListener startWatchingListener : cornea$startWatchingListeners) {
                startWatchingListener.onStartWatching(() -> cornea$startWatchingListeners.remove(startWatchingListener), player);
            }
        }
    }

    @Inject(method = "stopWatching(Lnet/minecraft/server/network/ServerPlayNetworkHandler;)Z", at = @At(value = "RETURN"))
    private void cornea$stopWatching(ServerPlayNetworkHandler player, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ()) {
            for (StopWatchingListener stopWatchingListener : cornea$stopWatchingListeners) {
                stopWatchingListener.onStopWatching(() -> cornea$stopWatchingListeners.remove(stopWatchingListener), player);
            }
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Leu/pb4/polymer/virtualentity/api/ElementHolder;onTick()V"))
    private void cornea$tick(CallbackInfo ci) {
        for (TickListener tickListener : cornea$tickListeners) {
            tickListener.onTick(() -> cornea$tickListeners.remove(tickListener));
        }

        for (VirtualElement element : elements) {
            if (element instanceof AbstractElementHook hook) {
                for (AbstractElementHook.TickListener tickListener : hook.cornea$getTickListeners()) {
                    tickListener.onTick(() -> hook.cornea$removeTickListener(tickListener));
                }
            }
        }
    }
}
