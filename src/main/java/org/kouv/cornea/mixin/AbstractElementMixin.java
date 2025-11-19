package org.kouv.cornea.mixin;

import eu.pb4.polymer.virtualentity.api.elements.AbstractElement;
import eu.pb4.polymer.virtualentity.api.elements.VirtualElement;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.kouv.cornea.elements.AbstractElementHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Objects;

@Mixin(value = AbstractElement.class, remap = false)
public abstract class AbstractElementMixin implements AbstractElementHook, VirtualElement {
    @Unique
    private Vec3d cornea$offsetVelocity = Vec3d.ZERO;
    @Unique
    private @Nullable Entity cornea$velocityRef;

    @Override
    public Vec3d cornea$getOffsetVelocity() {
        return cornea$offsetVelocity;
    }

    @Override
    public void cornea$setOffsetVelocity(Vec3d offsetVelocity) {
        Objects.requireNonNull(offsetVelocity);
        cornea$offsetVelocity = offsetVelocity;
    }

    @Override
    public @Nullable Entity cornea$getVelocityRef() {
        return cornea$velocityRef;
    }

    @Override
    public void cornea$setVelocityRef(@Nullable Entity velocityRef) {
        Objects.requireNonNull(velocityRef);
        cornea$velocityRef = velocityRef;
    }
}
