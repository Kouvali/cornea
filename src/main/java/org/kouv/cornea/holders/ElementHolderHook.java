package org.kouv.cornea.holders;

import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.jetbrains.annotations.Nullable;
import org.kouv.cornea.data.Attributes;

@SuppressWarnings("unused")
public interface ElementHolderHook {
    Attributes cornea$getAttributes();

    void cornea$addStartWatchingListener(StartWatchingListener listener);

    void cornea$removeStartWatchingListener(StartWatchingListener listener);

    void cornea$addStopWatchingListener(StopWatchingListener listener);

    void cornea$removeStopWatchingListener(StopWatchingListener listener);

    void cornea$addAttachmentChangeListener(AttachmentChangeListener listener);

    void cornea$removeAttachmentChangeListener(AttachmentChangeListener listener);

    void cornea$addTickListener(TickListener listener);

    void cornea$removeTickListener(TickListener listener);

    boolean cornea$isMarkedForDestruction();

    void cornea$setMarkedForDestruction(boolean marked);

    @FunctionalInterface
    interface StartWatchingListener {
        void onStartWatching(ServerPlayNetworkHandler networkHandler);
    }

    @FunctionalInterface
    interface StopWatchingListener {
        void onStopWatching(ServerPlayNetworkHandler networkHandler);
    }

    @FunctionalInterface
    interface AttachmentChangeListener {
        void onAttachmentChange(@Nullable HolderAttachment oldAttachment, @Nullable HolderAttachment newAttachment);
    }

    @FunctionalInterface
    interface TickListener {
        void onTick();
    }
}
