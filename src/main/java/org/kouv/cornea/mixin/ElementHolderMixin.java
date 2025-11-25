package org.kouv.cornea.mixin;

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

    @Shadow
    @Final
    private List<VirtualElement> elements;

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

    @Inject(
            method = "startWatching(Lnet/minecraft/server/network/ServerPlayNetworkHandler;)Z",
            at = @At(value = "RETURN")
    )
    private void cornea$invokeStartWatchingListeners(ServerPlayNetworkHandler networkHandler, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ()) {
            return;
        }

        for (VirtualElement element : cornea$getElementArray()) {
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

        for (VirtualElement element : cornea$getElementArray()) {
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
            at = @At(value = "TAIL")
    )
    private void cornea$invokeTickListeners(CallbackInfo ci) {
        for (VirtualElement element : cornea$getElementArray()) {
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
                    target = "Ljava/util/List;iterator()Ljava/util/Iterator;"
            )
    )
    private void cornea$applyElementOffsetPhysics(CallbackInfo ci) {
        for (VirtualElement element : cornea$getElementArray()) {
            if (!(element instanceof AbstractElementHook hook)) {
                continue;
            }

            double offsetGravity = hook.cornea$getOffsetGravity();
            if (Math.abs(offsetGravity) > 1E-6) {
                hook.cornea$setOffsetVelocity(hook.cornea$getOffsetVelocity().subtract(0, offsetGravity, 0));
            }

            Vec3d offsetVelocity = hook.cornea$getOffsetVelocity();
            if (offsetVelocity.lengthSquared() > 1E-6) {
                element.setOffset(element.getOffset().add(offsetVelocity));
            }
        }
    }

    @Unique
    private VirtualElement[] cornea$getElementArray() {
        return elements.toArray(new VirtualElement[0]);
    }
}
