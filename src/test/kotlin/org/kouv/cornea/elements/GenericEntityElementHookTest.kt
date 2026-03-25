package org.kouv.cornea.elements

import eu.pb4.polymer.virtualentity.api.ElementHolder
import eu.pb4.polymer.virtualentity.api.elements.GenericEntityElement
import io.mockk.*
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket
import net.minecraft.network.protocol.game.ClientboundBundlePacket
import net.minecraft.network.protocol.game.ClientboundEntityPositionSyncPacket
import net.minecraft.server.network.ServerGamePacketListenerImpl
import net.minecraft.world.phys.Vec3
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kouv.cornea.holders.elementHolder
import org.kouv.cornea.tests.FabricExtension
import kotlin.test.assertEquals

@ExtendWith(FabricExtension::class)
class GenericEntityElementHookTest {
    private lateinit var genericEntityElement: GenericEntityElement
    private lateinit var genericEntityElementHook: GenericEntityElementHook
    private lateinit var elementHolder: ElementHolder

    @BeforeEach
    fun setUp() {
        genericEntityElement = markerElement()
        genericEntityElementHook = genericEntityElement as GenericEntityElementHook

        elementHolder = elementHolder()
        elementHolder.addElement(genericEntityElement)
    }

    @Test
    fun `createSpawnPacket should include clientVelocity when set`() {
        // given
        val mockConnection = mockk<ServerGamePacketListenerImpl>(relaxed = true)
        val capturingSlot = slot<ClientboundBundlePacket>()

        every { mockConnection.send(capture(capturingSlot)) } just runs

        val clientVelocity = Vec3(1.0, 2.0, 3.0)

        genericEntityElementHook.`cornea$setClientVelocity`(clientVelocity)

        // when
        elementHolder.startWatching(mockConnection)

        // then
        val movement = capturingSlot.captured.subPackets()
            .asSequence()
            .filterIsInstance<ClientboundAddEntityPacket>()
            .first()
            .movement

        assertEquals(clientVelocity, movement)
    }

    @Test
    fun `createSpawnPacket should not include clientVelocity when not set`() {
        // given
        val mockConnection = mockk<ServerGamePacketListenerImpl>(relaxed = true)
        val capturingSlot = slot<ClientboundBundlePacket>()

        every { mockConnection.send(capture(capturingSlot)) } just runs

        // when
        elementHolder.startWatching(mockConnection)

        // then
        val movement = capturingSlot.captured.subPackets()
            .asSequence()
            .filterIsInstance<ClientboundAddEntityPacket>()
            .first()
            .movement

        assertEquals(Vec3.ZERO, movement)
    }

    @Test
    fun `sendPositionUpdates should include clientVelocity in position sync packet`() {
        // given
        val mockConnection = mockk<ServerGamePacketListenerImpl>(relaxed = true)
        val capturingSlot = slot<ClientboundEntityPositionSyncPacket>()

        every { mockConnection.send(capture(capturingSlot)) } just runs

        val clientVelocity = Vec3(1.0, 2.0, 3.0)

        genericEntityElement.setAlwaysSyncAbsolutePosition(true)
        genericEntityElementHook.`cornea$setClientVelocity`(clientVelocity)
        elementHolder.startWatching(mockConnection)

        // when
        genericEntityElement.offset = Vec3.Y_AXIS
        genericEntityElement.tick()

        // then
        val movement = capturingSlot.captured.values.deltaMovement

        assertEquals(clientVelocity, movement)
    }

    @Test
    fun `sendPositionUpdates should not include clientVelocity in position sync when not set`() {
        // given
        val mockConnection = mockk<ServerGamePacketListenerImpl>(relaxed = true)
        val capturingSlot = slot<ClientboundEntityPositionSyncPacket>()

        every { mockConnection.send(capture(capturingSlot)) } just runs

        genericEntityElement.setAlwaysSyncAbsolutePosition(true)
        elementHolder.startWatching(mockConnection)

        // when
        genericEntityElement.offset = Vec3.Y_AXIS
        genericEntityElement.tick()

        // then
        val movement = capturingSlot.captured.values.deltaMovement

        assertEquals(Vec3.ZERO, movement)
    }
}