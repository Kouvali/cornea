package org.kouv.cornea.elements

import eu.pb4.polymer.virtualentity.api.VirtualEntityUtils
import eu.pb4.polymer.virtualentity.api.elements.*
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.network.listener.ClientPlayPacketListener
import net.minecraft.network.packet.Packet
import net.minecraft.server.network.ServerPlayNetworkHandler
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.util.math.Vec3d
import org.joml.*
import org.kouv.cornea.events.Disposable
import org.kouv.cornea.math.matrix4f
import org.kouv.cornea.math.quaternionf
import org.kouv.cornea.math.vector3f

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

public var VirtualElement.drag: Double
    get() {
        return (this as VirtualElementHook).`cornea$getDrag`()
    }
    set(value) {
        (this as VirtualElementHook).`cornea$setDrag`(value)
    }

public var VirtualElement.gravity: Double
    get() {
        return (this as VirtualElementHook).`cornea$getGravity`()
    }
    set(value) {
        (this as VirtualElementHook).`cornea$setGravity`(value)
    }

public var VirtualElement.velocity: Vec3d
    get() {
        return (this as VirtualElementHook).`cornea$getVelocity`()
    }
    set(value) {
        (this as VirtualElementHook).`cornea$setVelocity`(value)
    }

public var VirtualElement.markedForRemoval: Boolean
    get() {
        return (this as VirtualElementHook).`cornea$isMarkedForRemoval`()
    }
    set(value) {
        (this as VirtualElementHook).`cornea$setMarkedForRemoval`(value)
    }

public fun VirtualElement.addAsPassengerTo(entity: Entity): Unit =
    VirtualEntityUtils.addVirtualPassenger(entity, *entityIds.toIntArray())

public val DisplayElement.transformation: Matrix4fc
    get() {
        return matrix4f {
            translationRotateScale(translation, leftRotation, scale)
            rotate(rightRotation)
        }
    }

public inline fun DisplayElement.transformation(block: Matrix4f.() -> Unit = {}): Matrix4f =
    matrix4f(block).also { setTransformation(it) }

public inline fun DisplayElement.transformation(mat: Matrix3fc, block: Matrix4f.() -> Unit = {}): Matrix4f =
    matrix4f(mat, block).also { setTransformation(it) }

public inline fun DisplayElement.transformation(mat: Matrix4fc, block: Matrix4f.() -> Unit = {}): Matrix4f =
    matrix4f(mat, block).also { setTransformation(it) }

public inline fun DisplayElement.transformation(mat: Matrix4x3fc, block: Matrix4f.() -> Unit = {}): Matrix4f =
    matrix4f(mat, block).also { setTransformation(it) }

public inline fun DisplayElement.transformation(mat: Matrix4dc, block: Matrix4f.() -> Unit = {}): Matrix4f =
    matrix4f(mat, block).also { setTransformation(it) }

public inline fun DisplayElement.transformation(
    m00: Float, m01: Float, m02: Float, m03: Float,
    m10: Float, m11: Float, m12: Float, m13: Float,
    m20: Float, m21: Float, m22: Float, m23: Float,
    m30: Float, m31: Float, m32: Float, m33: Float,
    block: Matrix4f.() -> Unit = {}
): Matrix4f = matrix4f(
    m00, m01, m02, m03,
    m10, m11, m12, m13,
    m20, m21, m22, m23,
    m30, m31, m32, m33,
    block
).also { setTransformation(it) }

public inline fun DisplayElement.transformation(
    col0: Vector4fc,
    col1: Vector4fc,
    col2: Vector4fc,
    col3: Vector4fc,
    block: Matrix4f.() -> Unit = {}
): Matrix4f = matrix4f(col0, col1, col2, col3, block).also { setTransformation(it) }

public inline fun DisplayElement.transform(block: Matrix4f.() -> Unit): Matrix4f =
    matrix4f(transformation, block).also { setTransformation(it) }

public inline fun DisplayElement.translation(block: Vector3f.() -> Unit = {}): Vector3f =
    vector3f(block).also { translation = it }

public inline fun DisplayElement.translation(d: Float, block: Vector3f.() -> Unit = {}): Vector3f =
    vector3f(d, block).also { translation = it }

public inline fun DisplayElement.translation(x: Float, y: Float, z: Float, block: Vector3f.() -> Unit = {}): Vector3f =
    vector3f(x, y, z, block).also { translation = it }

public inline fun DisplayElement.translation(v: Vector3fc, block: Vector3f.() -> Unit = {}): Vector3f =
    vector3f(v, block).also { translation = it }

public inline fun DisplayElement.translation(v: Vector3dc, block: Vector3f.() -> Unit = {}): Vector3f =
    vector3f(v, block).also { translation = it }

public inline fun DisplayElement.translation(v: Vector3ic, block: Vector3f.() -> Unit = {}): Vector3f =
    vector3f(v, block).also { translation = it }

public inline fun DisplayElement.translation(v: Vector2fc, z: Float, block: Vector3f.() -> Unit = {}): Vector3f =
    vector3f(v, z, block).also { translation = it }

public inline fun DisplayElement.translation(v: Vector2ic, z: Float, block: Vector3f.() -> Unit = {}): Vector3f =
    vector3f(v, z, block).also { translation = it }

public inline fun DisplayElement.translation(xyz: FloatArray, block: Vector3f.() -> Unit = {}): Vector3f =
    vector3f(xyz, block).also { translation = it }

public inline fun DisplayElement.scale(block: Vector3f.() -> Unit = {}): Vector3f =
    vector3f(1.0f, block).also { scale = it }

public inline fun DisplayElement.scale(d: Float, block: Vector3f.() -> Unit = {}): Vector3f =
    vector3f(d, block).also { scale = it }

public inline fun DisplayElement.scale(x: Float, y: Float, z: Float, block: Vector3f.() -> Unit = {}): Vector3f =
    vector3f(x, y, z, block).also { scale = it }

public inline fun DisplayElement.scale(v: Vector3fc, block: Vector3f.() -> Unit = {}): Vector3f =
    vector3f(v, block).also { scale = it }

public inline fun DisplayElement.scale(v: Vector3dc, block: Vector3f.() -> Unit = {}): Vector3f =
    vector3f(v, block).also { scale = it }

public inline fun DisplayElement.scale(v: Vector3ic, block: Vector3f.() -> Unit = {}): Vector3f =
    vector3f(v, block).also { scale = it }

public inline fun DisplayElement.scale(v: Vector2fc, z: Float, block: Vector3f.() -> Unit = {}): Vector3f =
    vector3f(v, z, block).also { scale = it }

public inline fun DisplayElement.scale(v: Vector2ic, z: Float, block: Vector3f.() -> Unit = {}): Vector3f =
    vector3f(v, z, block).also { scale = it }

public inline fun DisplayElement.scale(xyz: FloatArray, block: Vector3f.() -> Unit = {}): Vector3f =
    vector3f(xyz, block).also { scale = it }

public inline fun DisplayElement.leftRotation(block: Quaternionfc.() -> Unit = {}): Quaternionf =
    quaternionf(block).also { leftRotation = it }

public inline fun DisplayElement.leftRotation(
    x: Double,
    y: Double,
    z: Double,
    w: Double,
    block: Quaternionfc.() -> Unit = {}
): Quaternionf = quaternionf(x, y, z, w, block).also { leftRotation = it }

public inline fun DisplayElement.leftRotation(
    x: Float,
    y: Float,
    z: Float,
    w: Float,
    block: Quaternionfc.() -> Unit = {}
): Quaternionf = quaternionf(x, y, z, w, block).also { leftRotation = it }

public inline fun DisplayElement.leftRotation(source: Quaternionfc, block: Quaternionfc.() -> Unit = {}): Quaternionf =
    quaternionf(source, block).also { leftRotation = it }

public inline fun DisplayElement.leftRotation(source: Quaterniondc, block: Quaternionfc.() -> Unit = {}): Quaternionf =
    quaternionf(source, block).also { leftRotation = it }

public inline fun DisplayElement.leftRotation(
    axisAngle: AxisAngle4f,
    block: Quaternionfc.() -> Unit = {}
): Quaternionf = quaternionf(axisAngle, block).also { leftRotation = it }

public inline fun DisplayElement.leftRotation(
    axisAngle: AxisAngle4d,
    block: Quaternionfc.() -> Unit = {}
): Quaternionf = quaternionf(axisAngle, block).also { leftRotation = it }

public inline fun DisplayElement.rightRotation(block: Quaternionfc.() -> Unit = {}): Quaternionf =
    quaternionf(block).also { rightRotation = it }

public inline fun DisplayElement.rightRotation(
    x: Double,
    y: Double,
    z: Double,
    w: Double,
    block: Quaternionfc.() -> Unit = {}
): Quaternionf = quaternionf(x, y, z, w, block).also { rightRotation = it }

public inline fun DisplayElement.rightRotation(
    x: Float,
    y: Float,
    z: Float,
    w: Float,
    block: Quaternionfc.() -> Unit = {}
): Quaternionf = quaternionf(x, y, z, w, block).also { rightRotation = it }

public inline fun DisplayElement.rightRotation(source: Quaternionfc, block: Quaternionfc.() -> Unit = {}): Quaternionf =
    quaternionf(source, block).also { rightRotation = it }

public inline fun DisplayElement.rightRotation(source: Quaterniondc, block: Quaternionfc.() -> Unit = {}): Quaternionf =
    quaternionf(source, block).also { rightRotation = it }

public inline fun DisplayElement.rightRotation(
    axisAngle: AxisAngle4f,
    block: Quaternionfc.() -> Unit = {}
): Quaternionf = quaternionf(axisAngle, block).also { rightRotation = it }

public inline fun DisplayElement.rightRotation(
    axisAngle: AxisAngle4d,
    block: Quaternionfc.() -> Unit = {}
): Quaternionf = quaternionf(axisAngle, block).also { rightRotation = it }

public fun DisplayElement.startInterpolation(duration: Int) {
    startInterpolation()
    interpolationDuration = duration
}

public fun DisplayElement.startInterpolationIfDirty(duration: Int) {
    if (isTransformationDirty) {
        startInterpolation(duration)
    }
}

public class ElementStartWatchingScope @PublishedApi internal constructor(
    disposable: Disposable,
    public val networkHandler: ServerPlayNetworkHandler,
    private val packetSender: (Packet<ClientPlayPacketListener>) -> Unit
) :
    Disposable by disposable
{
    public val player: ServerPlayerEntity get() = networkHandler.player

    public fun sendPacket(packet: Packet<ClientPlayPacketListener>): Unit = packetSender(packet)
}

public inline fun VirtualElement.onStartWatching(crossinline block: ElementStartWatchingScope.() -> Unit): Disposable {
    this as VirtualElementHook

    lateinit var listener: VirtualElementHook.StartWatchingListener
    val disposable = Disposable {
        `cornea$removeStartWatchingListener`(listener)
    }

    listener = VirtualElementHook.StartWatchingListener { networkHandler, packetConsumer ->
        ElementStartWatchingScope(disposable, networkHandler, packetConsumer::accept).block()
    }

    `cornea$addStartWatchingListener`(listener)
    return disposable
}

public class ElementStopWatchingScope @PublishedApi internal constructor(
    disposable: Disposable,
    public val networkHandler: ServerPlayNetworkHandler,
    private val packetSender: (Packet<ClientPlayPacketListener>) -> Unit
) :
    Disposable by disposable
{
    public val player: ServerPlayerEntity get() = networkHandler.player

    public fun sendPacket(packet: Packet<ClientPlayPacketListener>): Unit = packetSender(packet)
}

public inline fun VirtualElement.onStopWatching(crossinline block: ElementStopWatchingScope.() -> Unit): Disposable {
    this as VirtualElementHook

    lateinit var listener: VirtualElementHook.StopWatchingListener
    val disposable = Disposable {
        `cornea$removeStopWatchingListener`(listener)
    }

    listener = VirtualElementHook.StopWatchingListener { networkHandler, packetConsumer ->
        ElementStopWatchingScope(disposable, networkHandler, packetConsumer::accept).block()
    }

    `cornea$addStopWatchingListener`(listener)
    return disposable
}

public class ElementTickScope @PublishedApi internal constructor(disposable: Disposable) :
    Disposable by disposable
{
    public var tickCount: Int = 0
        private set

    @PublishedApi
    internal fun update() {
        tickCount++
    }
}

public inline fun VirtualElement.onTick(crossinline block: ElementTickScope.() -> Unit): Disposable {
    this as VirtualElementHook

    lateinit var listener: VirtualElementHook.TickListener
    val disposable = Disposable {
        `cornea$removeTickListener`(listener)
    }

    val scope = ElementTickScope(disposable)
    listener = VirtualElementHook.TickListener {
        scope.block()
        scope.update()
    }

    `cornea$addTickListener`(listener)
    return disposable
}