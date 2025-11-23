package org.kouv.cornea.mixin;

import eu.pb4.polymer.virtualentity.api.attachment.EntityAttachment;
import org.kouv.cornea.attachments.EntityAttachmentHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@Mixin(value = EntityAttachment.class)
public abstract class EntityAttachmentMixin implements EntityAttachmentHook {
    @Unique
    private final List<PreEntityTickListener> cornea$preEntityTickListeners = new CopyOnWriteArrayList<>();
    @Unique
    private final List<PostEntityTickListener> cornea$postEntityTickListeners = new CopyOnWriteArrayList<>();

    @Override
    public List<? extends PreEntityTickListener> cornea$getPreEntityTickListeners() {
        return Collections.unmodifiableList(cornea$preEntityTickListeners);
    }

    @Override
    public void cornea$addPreEntityTickListener(PreEntityTickListener preEntityTickListener) {
        Objects.requireNonNull(preEntityTickListener);
        cornea$preEntityTickListeners.add(preEntityTickListener);
    }

    @Override
    public void cornea$removePreEntityTickListener(PreEntityTickListener preEntityTickListener) {
        Objects.requireNonNull(preEntityTickListener);
        cornea$preEntityTickListeners.remove(preEntityTickListener);
    }

    @Override
    public List<? extends PostEntityTickListener> cornea$getPostEntityTickListeners() {
        return Collections.unmodifiableList(cornea$postEntityTickListeners);
    }

    @Override
    public void cornea$addPostEntityTickListener(PostEntityTickListener postEntityTickListener) {
        Objects.requireNonNull(postEntityTickListener);
        cornea$postEntityTickListeners.add(postEntityTickListener);
    }

    @Override
    public void cornea$removePostEntityTickListener(PostEntityTickListener postEntityTickListener) {
        Objects.requireNonNull(postEntityTickListener);
        cornea$postEntityTickListeners.remove(postEntityTickListener);
    }
}
