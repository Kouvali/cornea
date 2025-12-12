package org.kouv.cornea.attachments;

@SuppressWarnings("unused")
public interface EntityAttachmentHook {
    void cornea$triggerEntityTickListeners();

    void cornea$addEntityTickListener(EntityTickListener listener);

    void cornea$removeEntityTickListener(EntityTickListener listener);

    @FunctionalInterface
    interface EntityTickListener {
        void onEntityTick();
    }
}
