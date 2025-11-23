package org.kouv.cornea.attachments;

import java.util.List;

@SuppressWarnings("unused")
public interface EntityAttachmentHook {
    List<? extends PreEntityTickListener> cornea$getPreEntityTickListeners();

    void cornea$addPreEntityTickListener(PreEntityTickListener preEntityTickListener);

    void cornea$removePreEntityTickListener(PreEntityTickListener preEntityTickListener);

    List<? extends PostEntityTickListener> cornea$getPostEntityTickListeners();

    void cornea$addPostEntityTickListener(PostEntityTickListener postEntityTickListener);

    void cornea$removePostEntityTickListener(PostEntityTickListener postEntityTickListener);

    @FunctionalInterface
    interface PreEntityTickListener {
        void onPreEntityTick();
    }

    @FunctionalInterface
    interface PostEntityTickListener {
        void onPostEntityTick();
    }
}
