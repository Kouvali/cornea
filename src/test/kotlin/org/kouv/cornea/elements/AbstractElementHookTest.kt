package org.kouv.cornea.elements

import eu.pb4.polymer.virtualentity.api.ElementHolder
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment
import eu.pb4.polymer.virtualentity.api.elements.AbstractElement
import io.mockk.*
import net.minecraft.server.network.ServerPlayNetworkHandler
import net.minecraft.util.math.Vec3d
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.kouv.cornea.holders.elementHolder
import org.kouv.cornea.tests.FabricExtension
import kotlin.test.Test
import kotlin.test.assertEquals

@ExtendWith(FabricExtension::class)
class AbstractElementHookTest {
    private lateinit var abstractElement: AbstractElement
    private lateinit var abstractElementHook: AbstractElementHook
    private lateinit var elementHolder: ElementHolder

    @BeforeEach
    fun setUp() {
        abstractElement = itemDisplayElement()
        abstractElementHook = abstractElement as AbstractElementHook
        elementHolder = elementHolder()
        elementHolder.addElement(abstractElement)
    }

    @Test
    fun `startWatching should invoke startWatching listeners`() {
        // given
        val mockNetworkHandler = mockk<ServerPlayNetworkHandler>(relaxed = true)
        val mockListener = mockk<AbstractElementHook.StartWatchingListener>()

        every { mockListener.onStartWatching(any()) } just runs

        abstractElementHook.`cornea$addStartWatchingListener`(mockListener)

        // when
        elementHolder.startWatching(mockNetworkHandler)

        // then
        verify { mockListener.onStartWatching(any()) }
    }

    @Test
    fun `stopWatching should invoke stopWatching listeners`() {
        // given
        val mockNetworkHandler = mockk<ServerPlayNetworkHandler>(relaxed = true)
        val mockListener = mockk<AbstractElementHook.StopWatchingListener>()

        every { mockListener.onStopWatching(any()) } just runs

        abstractElementHook.`cornea$addStopWatchingListener`(mockListener)
        elementHolder.startWatching(mockNetworkHandler)

        // when
        elementHolder.stopWatching(mockNetworkHandler)

        // then
        verify { mockListener.onStopWatching(any()) }
    }

    @Test
    fun `tick should apply drag to velocity when attachment is not null`() {
        // given
        val mockkAttachment = mockk<HolderAttachment>(relaxed = true)
        val offsetVelocity = Vec3d(1.0, 2.0, 3.0)
        val offsetDrag = 0.8

        elementHolder.attachment = mockkAttachment
        abstractElement.offsetVelocity = offsetVelocity
        abstractElement.offsetDrag = offsetDrag

        // when
        elementHolder.tick()

        // then
        assertEquals(offsetVelocity.multiply(offsetDrag), abstractElement.offsetVelocity)
    }

    @Test
    fun `tick should apply gravity to offset when attachment is not null`() {
        // given
        val mockkAttachment = mockk<HolderAttachment>(relaxed = true)
        val offset = Vec3d(1.0, 2.0, 3.0)
        val offsetGravity = 0.05

        elementHolder.attachment = mockkAttachment
        abstractElement.offset = offset
        abstractElement.offsetGravity = offsetGravity

        // when
        elementHolder.tick()

        // then
        assertEquals(offset.subtract(0.0, offsetGravity, 0.0), abstractElement.offset)
    }

    @Test
    fun `tick should apply velocity to offset when attachment is not null`() {
        // given
        val mockkAttachment = mockk<HolderAttachment>(relaxed = true)
        val offset = Vec3d(1.0, 2.0, 3.0)
        val offsetVelocity = Vec3d(3.0, 2.0, 1.0)

        elementHolder.attachment = mockkAttachment
        abstractElement.offset = offset
        abstractElement.offsetVelocity = offsetVelocity

        // when
        elementHolder.tick()

        // then
        assertEquals(offset.add(offsetVelocity), abstractElement.offset)
    }

    @Test
    fun `tick should invoke tick listeners when attachment is not null`() {
        // given
        val mockkAttachment = mockk<HolderAttachment>(relaxed = true)
        val mockListener = mockk<AbstractElementHook.TickListener>()

        every { mockListener.onTick() } just runs

        elementHolder.attachment = mockkAttachment
        abstractElementHook.`cornea$addTickListener`(mockListener)

        // when
        elementHolder.tick()

        // then
        verify { mockListener.onTick() }
    }
}