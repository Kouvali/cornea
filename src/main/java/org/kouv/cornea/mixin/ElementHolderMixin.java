package org.kouv.cornea.mixin;

import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.jetbrains.annotations.Nullable;
import org.kouv.cornea.data.Attributes;
import org.kouv.cornea.holders.ElementHolderHook;
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
    private @Nullable Attributes cornea$attributes = null;
    @Unique
    private @Nullable List<StartWatchingListener> cornea$startWatchingListeners = null;
    @Unique
    private @Nullable List<StopWatchingListener> cornea$stopWatchingListeners = null;
    @Unique
    private @Nullable List<AttachmentChangeListener> cornea$attachmentChangeListeners = null;
    @Unique
    private @Nullable List<TickListener> cornea$tickListeners = null;
    @Unique
    private boolean cornea$markedForDestruction = false;

    @Shadow
    public abstract void destroy();

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
    public void cornea$addAttachmentChangeListener(AttachmentChangeListener listener) {
        Objects.requireNonNull(listener);
        if (cornea$attachmentChangeListeners == null) {
            cornea$attachmentChangeListeners = new CopyOnWriteArrayList<>();
        }

        cornea$attachmentChangeListeners.add(listener);
    }

    @Override
    public void cornea$removeAttachmentChangeListener(AttachmentChangeListener listener) {
        Objects.requireNonNull(listener);
        if (cornea$attachmentChangeListeners != null) {
            cornea$attachmentChangeListeners.remove(listener);
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
    public boolean cornea$isMarkedForDestruction() {
        return cornea$markedForDestruction;
    }

    @Override
    public void cornea$setMarkedForDestruction(boolean marked) {
        cornea$markedForDestruction = marked;
    }

    @Inject(
            method = "startWatching(Lnet/minecraft/server/network/ServerPlayNetworkHandler;)Z",
            at = @At(value = "RETURN")
    )
    private void cornea$invokeStartWatchingListeners(ServerPlayNetworkHandler networkHandler, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ()) {
            return;
        }

        if (cornea$startWatchingListeners != null) {
            for (StartWatchingListener listener : cornea$startWatchingListeners) {
                listener.onStartWatching(networkHandler);
            }
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

        if (cornea$stopWatchingListeners != null) {
            for (StopWatchingListener listener : cornea$stopWatchingListeners) {
                listener.onStopWatching(networkHandler);
            }
        }
    }

    @Inject(
            method = "onAttachmentSet",
            at = @At(value = "TAIL")
    )
    private void cornea$invokeAttachmentChangeListenersOnSet(HolderAttachment attachment, @Nullable HolderAttachment oldAttachment, CallbackInfo ci) {
        if (cornea$attachmentChangeListeners != null) {
            for (AttachmentChangeListener listener : cornea$attachmentChangeListeners) {
                listener.onAttachmentChange(oldAttachment, attachment);
            }
        }
    }

    @Inject(
            method = "onAttachmentRemoved",
            at = @At(value = "TAIL")
    )
    private void cornea$invokeAttachmentChangeListenersOnRemove(HolderAttachment oldAttachment, CallbackInfo ci) {
        if (cornea$attachmentChangeListeners != null) {
            for (AttachmentChangeListener listener : cornea$attachmentChangeListeners) {
                listener.onAttachmentChange(oldAttachment, null);
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
    private void cornea$processPendingDestruction(CallbackInfo ci) {
        if (cornea$markedForDestruction) {
            ci.cancel();
            destroy();
        }
    }
}
