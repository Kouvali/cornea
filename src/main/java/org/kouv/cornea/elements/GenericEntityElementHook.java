package org.kouv.cornea.elements;

import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public interface GenericEntityElementHook {
    @Nullable Vec3 cornea$getClientVelocity();

    void cornea$setClientVelocity(@Nullable Vec3 clientVelocity);
}
