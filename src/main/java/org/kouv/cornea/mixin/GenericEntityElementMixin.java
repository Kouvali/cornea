package org.kouv.cornea.mixin;

import eu.pb4.polymer.virtualentity.api.elements.GenericEntityElement;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.kouv.cornea.elements.GenericEntityElementHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = GenericEntityElement.class)
public abstract class GenericEntityElementMixin implements GenericEntityElementHook {
    @Unique
    private @Nullable Vec3 cornea$clientVelocity = null;

    @Override
    public @Nullable Vec3 cornea$getClientVelocity() {
        return cornea$clientVelocity;
    }

    @Override
    public void cornea$setClientVelocity(@Nullable Vec3 clientVelocity) {
        cornea$clientVelocity = clientVelocity;
    }

    @ModifyArg(
            method = "createSpawnPacket",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/protocol/game/ClientboundAddEntityPacket;<init>(ILjava/util/UUID;DDDFFLnet/minecraft/world/entity/EntityType;ILnet/minecraft/world/phys/Vec3;D)V"
            ),
            index = 9
    )
    private Vec3 cornea$injectSpawnVelocity(Vec3 original) {
        return cornea$clientVelocity == null ? original : cornea$clientVelocity;
    }

    @ModifyArg(
            method = "sendPositionUpdates",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/PositionMoveRotation;<init>(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;FF)V"
            ),
            index = 1
    )
    private Vec3 cornea$injectSyncVelocity(Vec3 original) {
        return cornea$clientVelocity == null ? original : cornea$clientVelocity;
    }
}
