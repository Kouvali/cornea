package org.kouv.cornea.mixin;

import eu.pb4.polymer.virtualentity.api.elements.DisplayElement;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionfc;
import org.joml.Vector3fc;
import org.kouv.cornea.elements.DisplayElementHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = DisplayElement.class, remap = false)
public abstract class DisplayElementMixin implements DisplayElementHook {
    @Unique
    private final Matrix4f cornea$cachedMatrix = new Matrix4f();
    @Unique
    private boolean cornea$matrixDirty = true;

    @Shadow
    public abstract Vector3fc getTranslation();

    @Shadow
    public abstract Quaternionfc getLeftRotation();

    @Shadow
    public abstract Vector3fc getScale();

    @Shadow
    public abstract Quaternionfc getRightRotation();

    @Override
    public Matrix4fc cornea$getTransformation() {
        if (!cornea$matrixDirty) {
            return cornea$cachedMatrix;
        }

        cornea$cachedMatrix.translationRotateScale(
                getTranslation(),
                getLeftRotation(),
                getScale()
        );
        cornea$cachedMatrix.rotate(getRightRotation());
        cornea$matrixDirty = false;
        return cornea$cachedMatrix;
    }

    @Inject(
            method = "setTransformation(Lorg/joml/Matrix4f;)V",
            at = @At(value = "HEAD")
    )
    private void cornea$markAsNotDirty(Matrix4f matrix, CallbackInfo ci) {
        cornea$cachedMatrix.set(matrix);
        cornea$matrixDirty = false;
    }

    @Inject(
            method = {
                    "setTranslation",
                    "setScale",
                    "setLeftRotation",
                    "setRightRotation",
                    "setTransformation(Lorg/joml/Matrix4x3f;)V",
                    "setTransformation(Lnet/minecraft/util/math/AffineTransformation;)V"
            },
            at = @At(value = "HEAD")
    )
    private void cornea$markAsDirty(CallbackInfo ci) {
        cornea$matrixDirty = true;
    }
}
