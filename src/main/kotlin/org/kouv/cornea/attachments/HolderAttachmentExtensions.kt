package org.kouv.cornea.attachments

import eu.pb4.polymer.virtualentity.api.ElementHolder
import eu.pb4.polymer.virtualentity.api.attachment.*
import net.minecraft.core.BlockPos
import net.minecraft.resources.Identifier
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.chunk.LevelChunk
import net.minecraft.world.phys.Vec3
import org.kouv.cornea.annotations.ExperimentalPolymerApi
import org.kouv.cornea.annotations.InternalPolymerApi

@InternalPolymerApi
public inline fun blockBoundAttachment(
    holder: ElementHolder,
    chunk: LevelChunk,
    state: BlockState,
    blockPos: BlockPos,
    pos: Vec3,
    isTicking: Boolean,
    block: BlockBoundAttachment.() -> Unit = {}
): BlockBoundAttachment = BlockBoundAttachment(holder, chunk, state, blockPos, pos, isTicking).apply(block)

@ExperimentalPolymerApi
public inline fun blockBoundAttachmentOf(
    holder: ElementHolder,
    world: ServerLevel,
    blockPos: BlockPos,
    state: BlockState,
    block: BlockBoundAttachment.() -> Unit = {}
): BlockBoundAttachment? = BlockBoundAttachment.of(holder, world, blockPos, state)?.apply(block)

@ExperimentalPolymerApi
public inline fun blockBoundAttachmentOf(
    holder: ElementHolder,
    world: ServerLevel,
    chunk: LevelChunk,
    blockPos: BlockPos,
    state: BlockState,
    block: BlockBoundAttachment.() -> Unit = {}
): BlockBoundAttachment? = BlockBoundAttachment.of(holder, world, chunk, blockPos, state)?.apply(block)

@ExperimentalPolymerApi
public inline fun blockBoundAttachmentFromMoving(
    holder: ElementHolder,
    world: ServerLevel,
    pos: BlockPos,
    state: BlockState,
    block: BlockBoundAttachment.() -> Unit = {}
): BlockBoundAttachment? = BlockBoundAttachment.fromMoving(holder, world, pos, state)?.apply(block)

public inline fun chunkAttachment(
    holder: ElementHolder,
    chunk: LevelChunk,
    pos: Vec3,
    isTicking: Boolean,
    block: ChunkAttachment.() -> Unit = {}
): ChunkAttachment = ChunkAttachment(holder, chunk, pos, isTicking).apply(block)

public inline fun chunkAttachmentOf(
    holder: ElementHolder,
    world: ServerLevel,
    pos: BlockPos,
    block: HolderAttachment.() -> Unit = {}
): HolderAttachment = ChunkAttachment.of(holder, world, pos).apply(block)

public inline fun chunkAttachmentOfTicking(
    holder: ElementHolder,
    world: ServerLevel,
    pos: BlockPos,
    block: HolderAttachment.() -> Unit = {}
): HolderAttachment = ChunkAttachment.ofTicking(holder, world, pos).apply(block)

public inline fun chunkAttachmentOf(
    holder: ElementHolder,
    world: ServerLevel,
    pos: Vec3,
    block: HolderAttachment.() -> Unit = {}
): HolderAttachment = ChunkAttachment.of(holder, world, pos).apply(block)

public inline fun chunkAttachmentOfTicking(
    holder: ElementHolder,
    world: ServerLevel,
    pos: Vec3,
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
    world: ServerLevel,
    noinline posSupplier: () -> Vec3,
    block: ManualAttachment.() -> Unit = {}
): ManualAttachment = ManualAttachment(holder, world, posSupplier).apply(block)