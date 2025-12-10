package org.kouv.cornea.mixin;

import eu.pb4.polymer.virtualentity.api.attachment.EntityAttachment;
import org.kouv.cornea.attachments.EntityAttachmentHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@Mixin(value = EntityAttachment.class, remap = false)
public abstract class EntityAttachmentMixin implements EntityAttachmentHook {
    @Unique
    private final List<PreEntityTickListener> cornea$preEntityTickListeners = new CopyOnWriteArrayList<>();
    @Unique
    private final List<PostEntityTickListener> cornea$postEntityTickListeners = new CopyOnWriteArrayList<>();

    @Override
    public void cornea$triggerPreEntityTickListeners() {
        cornea$preEntityTickListeners.forEach(PreEntityTickListener::onPreEntityTick);
    }

    @Override
    public void cornea$addPreEntityTickListener(PreEntityTickListener listener) {
        Objects.requireNonNull(listener);
        cornea$preEntityTickListeners.add(listener);
    }

    @Override
    public void cornea$removePreEntityTickListener(PreEntityTickListener listener) {
        Objects.requireNonNull(listener);
        cornea$preEntityTickListeners.remove(listener);
    }

    @Override
    public void cornea$triggerPostEntityTickListeners() {
        cornea$postEntityTickListeners.forEach(PostEntityTickListener::onPostEntityTick);
    }

    @Override
    public void cornea$addPostEntityTickListener(PostEntityTickListener listener) {
        Objects.requireNonNull(listener);
        cornea$postEntityTickListeners.add(listener);
    }

    @Override
    public void cornea$removePostEntityTickListener(PostEntityTickListener listener) {
        Objects.requireNonNull(listener);
        cornea$postEntityTickListeners.remove(listener);
    }
}
