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
    private final List<EntityTickListener> cornea$entityTickListeners = new CopyOnWriteArrayList<>();

    @Override
    public void cornea$triggerEntityTickListeners() {
        cornea$entityTickListeners.forEach(EntityTickListener::onEntityTick);
    }

    @Override
    public void cornea$addEntityTickListener(EntityTickListener listener) {
        Objects.requireNonNull(listener);
        cornea$entityTickListeners.add(listener);
    }

    @Override
    public void cornea$removeEntityTickListener(EntityTickListener listener) {
        Objects.requireNonNull(listener);
        cornea$entityTickListeners.remove(listener);
    }
}
