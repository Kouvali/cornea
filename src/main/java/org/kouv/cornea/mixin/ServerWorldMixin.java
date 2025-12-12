package org.kouv.cornea.mixin;

import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.impl.HolderAttachmentHolder;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import org.kouv.cornea.attachments.EntityAttachmentHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ServerWorld.class)
public abstract class ServerWorldMixin {
    @Inject(method = "tickEntity", at = @At(value = "TAIL"))
    private void cornea$invokeAttachmentTickListeners(Entity entity, CallbackInfo ci) {
        for (HolderAttachment attachment : cornea$getAttachments(entity)) {
            if (attachment instanceof EntityAttachmentHook hook) {
                hook.cornea$triggerEntityTickListeners();
            }
        }
    }

    @Unique
    private HolderAttachment[] cornea$getAttachments(Entity entity) {
        return ((HolderAttachmentHolder) entity).polymerVE$getHolders().toArray(new HolderAttachment[0]);
    }
}
