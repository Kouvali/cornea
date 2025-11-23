package org.kouv.cornea.mixin;

import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.impl.HolderAttachmentHolder;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import org.kouv.cornea.attachments.EntityAttachmentHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(value = ServerWorld.class)
public abstract class SeverWorldMixin {
    @Inject(
            method = "tickEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;tick()V"
            )
    )
    private void cornea$invokeAttachmentPreTickListeners(Entity entity, CallbackInfo ci) {
        Collection<? extends HolderAttachment> attachments = ((HolderAttachmentHolder) entity).polymerVE$getHolders();
        for (HolderAttachment attachment : attachments) {
            if (attachment instanceof EntityAttachmentHook hook) {
                hook.cornea$getPreEntityTickListeners().forEach(EntityAttachmentHook.PreEntityTickListener::onPreEntityTick);
            }
        }
    }

    @Inject(
            method = "tickEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;tick()V",
                    shift = At.Shift.AFTER
            )
    )
    private void cornea$invokeAttachmentPostTickListeners(Entity entity, CallbackInfo ci) {
        Collection<? extends HolderAttachment> attachments = ((HolderAttachmentHolder) entity).polymerVE$getHolders();
        for (HolderAttachment attachment : attachments) {
            if (attachment instanceof EntityAttachmentHook hook) {
                hook.cornea$getPostEntityTickListeners().forEach(EntityAttachmentHook.PostEntityTickListener::onPostEntityTick);
            }
        }
    }
}
