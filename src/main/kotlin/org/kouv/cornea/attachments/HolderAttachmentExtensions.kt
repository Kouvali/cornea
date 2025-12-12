package org.kouv.cornea.attachments

import eu.pb4.polymer.virtualentity.api.ElementHolder
import eu.pb4.polymer.virtualentity.api.attachment.*
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.chunk.WorldChunk
import org.kouv.cornea.annotations.ExperimentalPolymerApi
import org.kouv.cornea.annotations.InternalPolymerApi
import org.kouv.cornea.events.Disposable

@InternalPolymerApi
public inline fun blockBoundAttachment(
    holder: ElementHolder,
    chunk: WorldChunk,
    state: BlockState,
    blockPos: BlockPos,
    pos: Vec3d,
    isTicking: Boolean,
    block: BlockBoundAttachment.() -> Unit = {}
): BlockBoundAttachment = BlockBoundAttachment(holder, chunk, state, blockPos, pos, isTicking).apply(block)

@ExperimentalPolymerApi
public inline fun blockBoundAttachmentOf(
    holder: ElementHolder,
    world: ServerWorld,
    blockPos: BlockPos,
    state: BlockState,
    block: BlockBoundAttachment.() -> Unit = {}
): BlockBoundAttachment? = BlockBoundAttachment.of(holder, world, blockPos, state)?.apply(block)

@ExperimentalPolymerApi
public inline fun blockBoundAttachmentOf(
    holder: ElementHolder,
    world: ServerWorld,
    chunk: WorldChunk,
    blockPos: BlockPos,
    state: BlockState,
    block: BlockBoundAttachment.() -> Unit = {}
): BlockBoundAttachment? = BlockBoundAttachment.of(holder, world, chunk, blockPos, state)?.apply(block)

@ExperimentalPolymerApi
public inline fun blockBoundAttachmentFromMoving(
    holder: ElementHolder,
    world: ServerWorld,
    pos: BlockPos,
    state: BlockState,
    block: BlockBoundAttachment.() -> Unit = {}
): BlockBoundAttachment? = BlockBoundAttachment.fromMoving(holder, world, pos, state)?.apply(block)

public inline fun chunkAttachment(
    holder: ElementHolder,
    chunk: WorldChunk,
    pos: Vec3d,
    isTicking: Boolean,
    block: ChunkAttachment.() -> Unit = {}
): ChunkAttachment = ChunkAttachment(holder, chunk, pos, isTicking).apply(block)

public inline fun chunkAttachmentOf(
    holder: ElementHolder,
    world: ServerWorld,
    pos: BlockPos,
    block: HolderAttachment.() -> Unit = {}
): HolderAttachment = ChunkAttachment.of(holder, world, pos).apply(block)

public inline fun chunkAttachmentOfTicking(
    holder: ElementHolder,
    world: ServerWorld,
    pos: BlockPos,
    block: HolderAttachment.() -> Unit = {}
): HolderAttachment = ChunkAttachment.ofTicking(holder, world, pos).apply(block)

public inline fun chunkAttachmentOf(
    holder: ElementHolder,
    world: ServerWorld,
    pos: Vec3d,
    block: HolderAttachment.() -> Unit = {}
): HolderAttachment = ChunkAttachment.of(holder, world, pos).apply(block)

public inline fun chunkAttachmentOfTicking(
    holder: ElementHolder,
    world: ServerWorld,
    pos: Vec3d,
    block: HolderAttachment.() -> Unit = {}
): HolderAttachment = ChunkAttachment.ofTicking(holder, world, pos).apply(block)

public inline fun entityAttachment(
    holder: ElementHolder,
    entity: Entity,
    isTicking: Boolean,
    block: EntityAttachment.() -> Unit = {}
): EntityAttachment = EntityAttachment(holder, entity, isTicking).apply(block)

public inline fun entityAttachmentOf(
    holder: ElementHolder,
    entity: Entity,
    block: EntityAttachment.() -> Unit = {}
): EntityAttachment = EntityAttachment.of(holder, entity).apply(block)

public inline fun entityAttachmentOfTicking(
    holder: ElementHolder,
    entity: Entity,
    block: EntityAttachment.() -> Unit = {}
): EntityAttachment = EntityAttachment.ofTicking(holder, entity).apply(block)

public inline fun identifiedUniqueEntityAttachment(
    id: Identifier,
    holder: ElementHolder,
    entity: Entity,
    isTicking: Boolean,
    block: IdentifiedUniqueEntityAttachment.() -> Unit = {}
): IdentifiedUniqueEntityAttachment = IdentifiedUniqueEntityAttachment(id, holder, entity, isTicking).apply(block)

public inline fun identifiedUniqueEntityAttachmentOf(
    id: Identifier,
    holder: ElementHolder,
    entity: Entity,
    block: IdentifiedUniqueEntityAttachment.() -> Unit = {}
): IdentifiedUniqueEntityAttachment = IdentifiedUniqueEntityAttachment.of(id, holder, entity).apply(block)

public inline fun identifiedUniqueEntityAttachmentOfTicking(
    id: Identifier,
    holder: ElementHolder,
    entity: Entity,
    block: IdentifiedUniqueEntityAttachment.() -> Unit = {}
): IdentifiedUniqueEntityAttachment = IdentifiedUniqueEntityAttachment.ofTicking(id, holder, entity).apply(block)

public inline fun manualAttachment(
    holder: ElementHolder,
    world: ServerWorld,
    noinline posSupplier: () -> Vec3d,
    block: ManualAttachment.() -> Unit = {}
): ManualAttachment = ManualAttachment(holder, world, posSupplier).apply(block)

public class EntityAttachmentEntityTickScope @PublishedApi internal constructor(
    disposable: Disposable,
    public val tickCount: Int
) : Disposable by disposable

public inline fun EntityAttachment.onEntityTick(crossinline block: EntityAttachmentEntityTickScope.() -> Unit): Disposable {
    this as EntityAttachmentHook

    lateinit var listener: EntityAttachmentHook.EntityTickListener
    val disposable = Disposable {
        `cornea$removeEntityTickListener`(listener)
    }

    var tickCount = 0
    listener = EntityAttachmentHook.EntityTickListener {
        @Suppress("AssignedValueIsNeverRead") EntityAttachmentEntityTickScope(disposable, tickCount++).block()
    }

    `cornea$addEntityTickListener`(listener)
    return disposable
}