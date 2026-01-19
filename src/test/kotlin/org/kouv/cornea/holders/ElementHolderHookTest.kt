package org.kouv.cornea.holders

import eu.pb4.polymer.virtualentity.api.ElementHolder
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment
import io.mockk.*
import net.minecraft.server.network.ServerPlayNetworkHandler
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.kouv.cornea.tests.FabricExtension
import kotlin.test.Test

@ExtendWith(FabricExtension::class)
class ElementHolderHookTest {
    private lateinit var elementHolder: ElementHolder
    private lateinit var elementHolderHook: ElementHolderHook

    @BeforeEach
    fun setUp() {
        elementHolder = elementHolder()
        elementHolderHook = elementHolder as ElementHolderHook
    }

    @Test
    fun `startWatching should invoke startWatching listeners`() {
        // given
        val mockNetworkHandler = mockk<ServerPlayNetworkHandler>(relaxed = true)
        val mockListener = mockk<ElementHolderHook.StartWatchingListener>()

        every { mockListener.onStartWatching(any()) } just runs

        elementHolderHook.`cornea$addStartWatchingListener`(mockListener)

        // when
        elementHolder.startWatching(mockNetworkHandler)

        // then
        verify { mockListener.onStartWatching(mockNetworkHandler) }
    }

    @Test
    fun `stopWatching should invoke stopWatching listeners`() {
        // given
        val mockNetworkHandler = mockk<ServerPlayNetworkHandler>(relaxed = true)
        val mockListener = mockk<ElementHolderHook.StopWatchingListener>()

        every { mockListener.onStopWatching(any()) } just runs

        elementHolder.startWatching(mockNetworkHandler)
        elementHolderHook.`cornea$addStopWatchingListener`(mockListener)

        // when
        elementHolder.stopWatching(mockNetworkHandler)

        // then
        verify { mockListener.onStopWatching(mockNetworkHandler) }
    }

    @Test
    fun `setAttachment should invoke attachmentChange listeners`() {
        // given
        val mockAttachment1 = mockk<HolderAttachment>(relaxed = true)
        val mockAttachment2 = mockk<HolderAttachment>(relaxed = true)
        val mockListener = mockk<ElementHolderHook.AttachmentChangeListener>()

        every { mockListener.onAttachmentChange(any(), any()) } just runs

        elementHolderHook.`cornea$addAttachmentChangeListener`(mockListener)

        // when
        elementHolder.attachment = mockAttachment1

        // then
        verify { mockListener.onAttachmentChange(null, mockAttachment1) }

        // when
        elementHolder.attachment = mockAttachment2

        // then
        verify { mockListener.onAttachmentChange(mockAttachment1, mockAttachment2) }

        // when
        elementHolder.attachment = null

        // then
        verify { mockListener.onAttachmentChange(mockAttachment2, null) }
    }

    @Test
    fun `tick should invoke tick listeners when attachment is not null`() {
        // given
        val mockAttachment = mockk<HolderAttachment>(relaxed = true)
        val mockListener = mockk<ElementHolderHook.TickListener>()

        every { mockListener.onTick() } just runs

        elementHolder.attachment = mockAttachment
        elementHolderHook.`cornea$addTickListener`(mockListener)

        // when
        elementHolder.tick()

        // then
        verify { mockListener.onTick() }
    }

    @Test
    fun `tick should not invoke tick listeners when attachment is null`() {
        // given
        val mockListener = mockk<ElementHolderHook.TickListener>()

        every { mockListener.onTick() } just runs

        elementHolderHook.`cornea$addTickListener`(mockListener)

        // when
        elementHolder.tick()

        // then
        verify { mockListener wasNot called }
    }

    @Test
    fun `tick should destroy holder and cancel tick logic when marked for destruction`() {
        // given
        val mockAttachment = mockk<HolderAttachment>(relaxed = true)
        val mockListener = mockk<ElementHolderHook.TickListener>()

        every { mockListener.onTick() } just runs

        elementHolder.attachment = mockAttachment
        elementHolderHook.`cornea$addTickListener`(mockListener)
        elementHolderHook.`cornea$setMarkedForDestruction`(true)

        // when
        elementHolder.tick()

        // then
        verify { mockAttachment.destroy() }
        verify(exactly = 0) { mockListener.onTick() }
    }
}