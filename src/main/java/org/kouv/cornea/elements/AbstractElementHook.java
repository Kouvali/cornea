package org.kouv.cornea.elements;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public interface AbstractElementHook {
    Vec3d cornea$getOffsetVelocity();

    void cornea$setOffsetVelocity(Vec3d offsetVelocity);

    @Nullable Entity cornea$getVelocityRef();

    void cornea$setVelocityRef(@Nullable Entity velocityRef);
}
