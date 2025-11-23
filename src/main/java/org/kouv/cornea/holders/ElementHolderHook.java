package org.kouv.cornea.holders;

import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public interface ElementHolderHook {
    void cornea$addStartWatchingListener(StartWatchingListener startWatchingListener);

    void cornea$removeStartWatchingListener(StartWatchingListener startWatchingListener);

    void cornea$addStopWatchingListener(StopWatchingListener stopWatchingListener);

    void cornea$removeStopWatchingListener(StopWatchingListener stopWatchingListener);

    void cornea$addAttachmentChangeListener(AttachmentChangeListener attachmentChangeListener);

    void cornea$removeAttachmentChangeListener(AttachmentChangeListener attachmentChangeListener);

    void cornea$addTickListener(TickListener tickListener);

    void cornea$removeTickListener(TickListener tickListener);

    @FunctionalInterface
    interface StartWatchingListener {
        void onStartWatching(ServerPlayNetworkHandler player);
    }

    @FunctionalInterface
    interface StopWatchingListener {
        void onStopWatching(ServerPlayNetworkHandler player);
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
