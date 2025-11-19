package org.kouv.cornea.elements

import eu.pb4.polymer.virtualentity.api.VirtualEntityUtils
import eu.pb4.polymer.virtualentity.api.elements.*
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.util.math.Vec3d
import org.joml.*
import org.kouv.cornea.math.matrix4f

public inline fun blockDisplayElement(block: BlockDisplayElement.() -> Unit = {}): BlockDisplayElement =
    BlockDisplayElement().apply(block)

public inline fun blockDisplayElement(
    state: BlockState,
    block: BlockDisplayElement.() -> Unit = {}
): BlockDisplayElement = BlockDisplayElement(state).apply(block)

public inline fun <T : Entity> entityElement(
    entity: T,
    world: ServerWorld,
    block: EntityElement<T>.() -> Unit = {}
): EntityElement<T> = EntityElement(entity, world).apply(block)

public inline fun <T : Entity> entityElement(
    entity: T,
    world: ServerWorld,
    handler: VirtualElement.InteractionHandler,
    block: EntityElement<T>.() -> Unit = {}
): EntityElement<T> = EntityElement(entity, world, handler).apply(block)

public inline fun <T : Entity> entityElement(
    type: EntityType<T>,
    world: ServerWorld,
    block: EntityElement<T>.() -> Unit = {}
): EntityElement<T> = EntityElement(type, world).apply(block)

public inline fun <T : Entity> entityElement(
    type: EntityType<T>,
    world: ServerWorld,
    handler: VirtualElement.InteractionHandler,
    block: EntityElement<T>.() -> Unit = {}
): EntityElement<T> = EntityElement(type, world, handler).apply(block)

public inline fun interactionElement(block: InteractionElement.() -> Unit = {}): InteractionElement =
    InteractionElement().apply(block)

public inline fun interactionElement(
    handler: VirtualElement.InteractionHandler,
    block: InteractionElement.() -> Unit = {}
): InteractionElement = InteractionElement(handler).apply(block)

public inline fun interactionElementRedirect(
    redirect: Entity,
    block: InteractionElement.() -> Unit = {}
): InteractionElement = InteractionElement.redirect(redirect).apply(block)

public inline fun itemDisplayElement(block: ItemDisplayElement.() -> Unit = {}): ItemDisplayElement =
    ItemDisplayElement().apply(block)

public inline fun itemDisplayElement(stack: ItemStack, block: ItemDisplayElement.() -> Unit = {}): ItemDisplayElement =
    ItemDisplayElement(stack).apply(block)

public inline fun itemDisplayElement(item: Item, block: ItemDisplayElement.() -> Unit = {}): ItemDisplayElement =
    ItemDisplayElement(item).apply(block)

public inline fun markerElement(block: MarkerElement.() -> Unit = {}): MarkerElement = MarkerElement().apply(block)

public inline fun mobAnchorElement(block: MobAnchorElement.() -> Unit = {}): MobAnchorElement =
    MobAnchorElement().apply(block)

public inline fun simpleEntityElement(
    type: EntityType<*>,
    block: SimpleEntityElement.() -> Unit = {}
): SimpleEntityElement = SimpleEntityElement(type).apply(block)

public inline fun textDisplayElement(block: TextDisplayElement.() -> Unit = {}): TextDisplayElement =
    TextDisplayElement().apply(block)

public inline fun textDisplayElement(text: Text, block: TextDisplayElement.() -> Unit = {}): TextDisplayElement =
    TextDisplayElement(text).apply(block)

public fun VirtualElement.addAsPassengerTo(entity: Entity): Unit =
    VirtualEntityUtils.addVirtualPassenger(entity, *entityIds.toIntArray())

public var AbstractElement.offsetVelocity: Vec3d
    get() {
        return (this as AbstractElementHook).`cornea$getOffsetVelocity`()
    }
    set(value) {
        (this as AbstractElementHook).`cornea$setOffsetVelocity`(value)
    }

public var AbstractElement.velocityRef: Entity?
    get() {
        return (this as AbstractElementHook).`cornea$getVelocityRef`()
    }
    set(value) {
        (this as AbstractElementHook).`cornea$setVelocityRef`(value)
    }

public inline fun DisplayElement.transformation(block: Matrix4f.() -> Unit = {}): Matrix4f =
    matrix4f(block).also { setTransformation(it) }

public inline fun DisplayElement.transformation(from: Matrix3fc, block: Matrix4f.() -> Unit = {}): Matrix4f =
    matrix4f(from, block).also { setTransformation(it) }

public inline fun DisplayElement.transformation(from: Matrix4fc, block: Matrix4f.() -> Unit = {}): Matrix4f =
    matrix4f(from, block).also { setTransformation(it) }

public inline fun DisplayElement.transformation(from: Matrix4x3fc, block: Matrix4f.() -> Unit = {}): Matrix4f =
    matrix4f(from, block).also { setTransformation(it) }

public inline fun DisplayElement.transformation(from: Matrix4dc, block: Matrix4f.() -> Unit = {}): Matrix4f =
    matrix4f(from, block).also { setTransformation(it) }

public fun DisplayElement.startInterpolation(duration: Int) {
    startInterpolation()
    interpolationDuration = duration
}

public fun DisplayElement.startInterpolationIfDirty(duration: Int) {
    if (isTransformationDirty) {
        startInterpolation(duration)
    }
}