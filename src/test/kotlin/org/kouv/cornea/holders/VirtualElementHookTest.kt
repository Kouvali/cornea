package org.kouv.cornea.holders

import eu.pb4.polymer.virtualentity.api.ElementHolder
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment
import eu.pb4.polymer.virtualentity.api.elements.VirtualElement
import io.mockk.*
import net.minecraft.server.network.ServerPlayNetworkHandler
import net.minecraft.util.math.Vec3d
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.kouv.cornea.elements.VirtualElementHook
import org.kouv.cornea.elements.itemDisplayElement
import org.kouv.cornea.tests.FabricExtension
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExtendWith(FabricExtension::class)
class VirtualElementHookTest {
    private lateinit var virtualElement: VirtualElement
    private lateinit var virtualElementHook: VirtualElementHook
    private lateinit var elementHolder: ElementHolder

    @BeforeEach
    fun setUp() {
        virtualElement = itemDisplayElement()
        virtualElementHook = virtualElement as VirtualElementHook
        elementHolder = elementHolder()
        elementHolder.addElement(virtualElement)
    }

    @Test
    fun `startWatching should invoke startWatching listeners`() {
        // given
        val mockNetworkHandler = mockk<ServerPlayNetworkHandler>(relaxed = true)
        val mockListener = mockk<VirtualElementHook.StartWatchingListener>()

        every { mockListener.onStartWatching(any()) } just runs

        virtualElementHook.`cornea$addStartWatchingListener`(mockListener)

        // when
        elementHolder.startWatching(mockNetworkHandler)

        // then
        verify { mockListener.onStartWatching(any()) }
    }

    @Test
    fun `stopWatching should invoke stopWatching listeners`() {
        // given
        val mockNetworkHandler = mockk<ServerPlayNetworkHandler>(relaxed = true)
        val mockListener = mockk<VirtualElementHook.StopWatchingListener>()

        every { mockListener.onStopWatching(any()) } just runs

        virtualElementHook.`cornea$addStopWatchingListener`(mockListener)
        elementHolder.startWatching(mockNetworkHandler)

        // when
        elementHolder.stopWatching(mockNetworkHandler)

        // then
        verify { mockListener.onStopWatching(any()) }
    }

    @Test
    fun `tick should invoke tick listeners when attachment is not null`() {
        // given
        val mockkAttachment = mockk<HolderAttachment>(relaxed = true)
        val mockListener = mockk<VirtualElementHook.TickListener>()

        every { mockListener.onTick() } just runs

        elementHolder.attachment = mockkAttachment
        virtualElementHook.`cornea$addTickListener`(mockListener)

        // when
        elementHolder.tick()

        // then
        verify { mockListener.onTick() }
    }

    @Test
    fun `tick should remove element and cancel tick logic when marked for removal`() {
        // given
        val mockkAttachment = mockk<HolderAttachment>(relaxed = true)
        val mockListener = mockk<VirtualElementHook.TickListener>()

        every { mockListener.onTick() } just runs

        elementHolder.attachment = mockkAttachment
        virtualElementHook.`cornea$addTickListener`(mockListener)
        virtualElementHook.`cornea$setMarkedForRemoval`(true)

        // when
        elementHolder.tick()

        // then
        assertTrue(virtualElement !in elementHolder.elements)
        verify(exactly = 0) { mockListener.onTick() }
    }

    @Test
    fun `tick should apply drag to velocity when attachment is not null`() {
        // given
        val mockkAttachment = mockk<HolderAttachment>(relaxed = true)
        val velocity = Vec3d(1.0, 2.0, 3.0)
        val drag = 0.8

        elementHolder.attachment = mockkAttachment
        virtualElementHook.`cornea$setVelocity`(velocity)
        virtualElementHook.`cornea$setDrag`(drag)

        // when
        elementHolder.tick()

        // then
        assertEquals(velocity.multiply(drag), virtualElementHook.`cornea$getVelocity`())
    }

    @Test
    fun `tick should apply gravity to offset when attachment is not null`() {
        // given
        val mockkAttachment = mockk<HolderAttachment>(relaxed = true)
        val offset = Vec3d(1.0, 2.0, 3.0)
        val gravity = 0.08

        elementHolder.attachment = mockkAttachment
        virtualElement.offset = offset
        virtualElementHook.`cornea$setGravity`(gravity)

        // when
        elementHolder.tick()

        // then
        assertEquals(offset.subtract(0.0, gravity, 0.0), virtualElement.offset)
    }

    @Test
    fun `tick should apply velocity to offset when attachment is not null`() {
        // given
        val mockkAttachment = mockk<HolderAttachment>(relaxed = true)
        val offset = Vec3d(1.0, 2.0, 3.0)
        val velocity = Vec3d(3.0, 2.0, 1.0)

        elementHolder.attachment = mockkAttachment
        virtualElement.offset = offset
        virtualElementHook.`cornea$setVelocity`(velocity)

        // when
        elementHolder.tick()

        // then
        assertEquals(offset.add(velocity), virtualElement.offset)
    }
}