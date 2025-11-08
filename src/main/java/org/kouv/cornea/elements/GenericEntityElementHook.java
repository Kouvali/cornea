package org.kouv.cornea.elements;

import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public interface GenericEntityElementHook {
    @Nullable Entity cornea$getVelocityRef();

    void cornea$setVelocityRef(@Nullable Entity velocityRef);
}
