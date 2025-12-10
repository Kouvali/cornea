package org.kouv.cornea.attachments;

@SuppressWarnings("unused")
public interface EntityAttachmentHook {
    void cornea$triggerPreEntityTickListeners();

    void cornea$addPreEntityTickListener(PreEntityTickListener listener);

    void cornea$removePreEntityTickListener(PreEntityTickListener listener);

    void cornea$triggerPostEntityTickListeners();

    void cornea$addPostEntityTickListener(PostEntityTickListener listener);

    void cornea$removePostEntityTickListener(PostEntityTickListener listener);

    @FunctionalInterface
    interface PreEntityTickListener {
        void onPreEntityTick();
    }

    @FunctionalInterface
    interface PostEntityTickListener {
        void onPostEntityTick();
    }
}
