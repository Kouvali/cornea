package org.kouv.cornea.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.VirtualElement;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.kouv.cornea.elements.AbstractElementHook;
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
    private final List<AttachmentChangeListener> cornea$attachmentChangeListeners = new CopyOnWriteArrayList<>();
    @Unique
    private final List<TickListener> cornea$tickListeners = new CopyOnWriteArrayList<>();
    @Unique
    private boolean cornea$autoDestroyIfEmpty = false;
    @Unique
    private boolean cornea$markedForDestruction = false;

    @Shadow
    @Final
    private List<VirtualElement> elements;

    @Shadow
    public abstract void destroy();

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
    public void cornea$addAttachmentChangeListener(AttachmentChangeListener listener) {
        Objects.requireNonNull(listener);
        cornea$attachmentChangeListeners.add(listener);
    }

    @Override
    public void cornea$removeAttachmentChangeListener(AttachmentChangeListener listener) {
        Objects.requireNonNull(listener);
        cornea$attachmentChangeListeners.remove(listener);
    }

    @Override
    public void cornea$addTickListener(TickListener listener) {
        Objects.requireNonNull(listener);
        cornea$tickListeners.add(listener);
    }

    @Override
    public void cornea$removeTickListener(TickListener listener) {
        Objects.requireNonNull(listener);
        cornea$tickListeners.remove(listener);
    }

    @Override
    public boolean cornea$isAutoDestroyIfEmpty() {
        return cornea$autoDestroyIfEmpty;
    }

    @Override
    public void cornea$setAutoDestroyIfEmpty(boolean autoDestroyIfEmpty) {
        cornea$autoDestroyIfEmpty = autoDestroyIfEmpty;
    }

    @Inject(
            method = "startWatching(Lnet/minecraft/server/network/ServerPlayNetworkHandler;)Z",
            at = @At(value = "RETURN")
    )
    private void cornea$invokeStartWatchingListeners(ServerPlayNetworkHandler networkHandler, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ()) {
            return;
        }

        for (VirtualElement element : elements.toArray(new VirtualElement[0])) {
            if (element instanceof AbstractElementHook hook) {
                hook.cornea$triggerStartWatchingListeners(networkHandler);
            }
        }

        for (StartWatchingListener listener : cornea$startWatchingListeners) {
            listener.onStartWatching(networkHandler);
        }
    }

    @Inject(
            method = "stopWatching(Lnet/minecraft/server/network/ServerPlayNetworkHandler;)Z",
            at = @At(value = "RETURN")
    )
    private void cornea$invokeStopWatchingListeners(ServerPlayNetworkHandler networkHandler, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ()) {
            return;
        }

        for (VirtualElement element : elements.toArray(new VirtualElement[0])) {
            if (element instanceof AbstractElementHook hook) {
                hook.cornea$triggerStopWatchingListeners(networkHandler);
            }
        }

        for (StopWatchingListener listener : cornea$stopWatchingListeners) {
            listener.onStopWatching(networkHandler);
        }
    }

    @Inject(
            method = "onAttachmentSet",
            at = @At(value = "TAIL")
    )
    private void cornea$invokeAttachmentChangeListenersOnSet(HolderAttachment attachment, @Nullable HolderAttachment oldAttachment, CallbackInfo ci) {
        for (AttachmentChangeListener listener : cornea$attachmentChangeListeners) {
            listener.onAttachmentChange(oldAttachment, attachment);
        }
    }

    @Inject(
            method = "onAttachmentRemoved",
            at = @At(value = "TAIL")
    )
    private void cornea$invokeAttachmentChangeListenersOnRemove(HolderAttachment oldAttachment, CallbackInfo ci) {
        for (AttachmentChangeListener listener : cornea$attachmentChangeListeners) {
            listener.onAttachmentChange(oldAttachment, null);
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
        for (VirtualElement element : elements.toArray(new VirtualElement[0])) {
            if (element instanceof AbstractElementHook hook) {
                hook.cornea$triggerTickListeners();
            }
        }

        for (TickListener listener : cornea$tickListeners) {
            listener.onTick();
        }
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Leu/pb4/polymer/virtualentity/api/elements/VirtualElement;tick()V"
            )
    )
    private void cornea$applyElementPhysics(CallbackInfo ci, @Local(name = "e") VirtualElement element) {
        if (element instanceof AbstractElementHook hook) {
            double gravity = hook.cornea$getGravity();
            if (Math.abs(gravity) > 1E-6) {
                hook.cornea$setVelocity(hook.cornea$getVelocity().subtract(0, gravity, 0));
            }

            double drag = hook.cornea$getDrag();
            if (Math.abs(drag - 1.0) > 1E-6) {
                hook.cornea$setVelocity(hook.cornea$getVelocity().multiply(drag));
            }

            Vec3d velocity = hook.cornea$getVelocity();
            if (velocity.lengthSquared() > 1E-6) {
                element.setOffset(element.getOffset().add(velocity));
            }
        }
    }

    @Inject(
            method = "tick",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void cornea$applyAutoDestroy(CallbackInfo ci) {
        if (cornea$markedForDestruction) {
            destroy();
            ci.cancel();
        }
    }

    @Inject(
            method = "removeElementWithoutUpdates",
            at = @At(value = "RETURN")
    )
    private void cornea$markForDestruction(VirtualElement element, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ() && cornea$isAutoDestroyIfEmpty() && elements.isEmpty()) {
            cornea$markedForDestruction = true;
        }
    }
}
