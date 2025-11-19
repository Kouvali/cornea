package org.kouv.cornea.mixin;

import eu.pb4.polymer.virtualentity.api.elements.AbstractElement;
import eu.pb4.polymer.virtualentity.api.elements.GenericEntityElement;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.kouv.cornea.elements.GenericEntityElementHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(value = GenericEntityElement.class, remap = false)
public abstract class GenericEntityElementMixin extends AbstractElement implements GenericEntityElementHook {
    @Unique
    private @Nullable Entity cornea$velocityRef;

    @Shadow
    public abstract int getEntityId();

    @Override
    public @Nullable Entity cornea$getVelocityRef() {
        return cornea$velocityRef;
    }

    @Override
    public void cornea$setVelocityRef(@Nullable Entity velocityRef) {
        Objects.requireNonNull(velocityRef);
        cornea$velocityRef = velocityRef;
    }

    @Inject(method = "notifyMove", at = @At(value = "TAIL"))
    private void cornea$notifyMove(Vec3d oldPos, Vec3d newPos, Vec3d delta, CallbackInfo ci) {
        if (getHolder() != null && cornea$velocityRef != null) {
            getHolder().sendPacket(new EntityVelocityUpdateS2CPacket(getEntityId(), cornea$velocityRef.getVelocity()));
        }
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    private void cornea$tick(CallbackInfo ci) {
        if (getHolder() != null && cornea$velocityRef != null) {
            getHolder().sendPacket(new EntityVelocityUpdateS2CPacket(getEntityId(), cornea$velocityRef.getVelocity()));
        }
    }
}
