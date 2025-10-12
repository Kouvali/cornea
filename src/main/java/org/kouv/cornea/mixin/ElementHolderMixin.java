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
import java.util.function.Consumer;

@Mixin(value = ElementHolder.class, remap = false)
public abstract class ElementHolderMixin implements ElementHolderHook {
    @Unique
    private final List<Consumer<? super ServerPlayNetworkHandler>> cornea$startWatchingListeners = new CopyOnWriteArrayList<>();
    @Unique
    private final List<Consumer<? super ServerPlayNetworkHandler>> cornea$stopWatchingListeners = new CopyOnWriteArrayList<>();
    @Unique
    private final List<Runnable> cornea$tickListeners = new CopyOnWriteArrayList<>();

    @Shadow
    @Final
    private List<VirtualElement> elements;

    @Override
    public Disposable cornea$addStartWatchingListener(Consumer<? super ServerPlayNetworkHandler> consumer) {
        Objects.requireNonNull(consumer);
        cornea$startWatchingListeners.add(consumer);
        return () -> cornea$startWatchingListeners.remove(consumer);
    }

    @Override
    public Disposable cornea$addStopWatchingListener(Consumer<? super ServerPlayNetworkHandler> consumer) {
        Objects.requireNonNull(consumer);
        cornea$stopWatchingListeners.add(consumer);
        return () -> cornea$stopWatchingListeners.remove(consumer);
    }

    @Override
    public Disposable cornea$addTickListener(Runnable runnable) {
        Objects.requireNonNull(runnable);
        cornea$tickListeners.add(runnable);
        return () -> cornea$tickListeners.remove(runnable);
    }

    @Inject(method = "startWatching(Lnet/minecraft/server/network/ServerPlayNetworkHandler;)Z", at = @At(value = "RETURN"))
    private void cornea$startWatching(ServerPlayNetworkHandler player, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ()) {
            cornea$startWatchingListeners.forEach(consumer -> consumer.accept(player));
        }
    }

    @Inject(method = "stopWatching(Lnet/minecraft/server/network/ServerPlayNetworkHandler;)Z", at = @At(value = "RETURN"))
    private void cornea$stopWatching(ServerPlayNetworkHandler player, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ()) {
            cornea$stopWatchingListeners.forEach(consumer -> consumer.accept(player));
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Leu/pb4/polymer/virtualentity/api/ElementHolder;onTick()V"))
    private void cornea$tick(CallbackInfo ci) {
        cornea$tickListeners.forEach(Runnable::run);
        for (VirtualElement element : elements) {
            if (element instanceof AbstractElementHook hook) {
                hook.cornea$getTickListeners().forEach(Runnable::run);
            }
        }
    }
}
