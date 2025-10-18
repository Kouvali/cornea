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

        every { mockListener.onStartWatching(any(), any()) } just runs

        elementHolderHook.`cornea$addStartWatchingListener`(mockListener)

        // when
        elementHolder.startWatching(mockNetworkHandler)

        // then
        verify { mockListener.onStartWatching(any(), mockNetworkHandler) }
    }

    @Test
    fun `stopWatching should invoke stopWatching listeners`() {
        // given
        val mockNetworkHandler = mockk<ServerPlayNetworkHandler>(relaxed = true)
        val mockListener = mockk<ElementHolderHook.StopWatchingListener>()

        every { mockListener.onStopWatching(any(), any()) } just runs

        elementHolder.startWatching(mockNetworkHandler)
        elementHolderHook.`cornea$addStopWatchingListener`(mockListener)

        // when
        elementHolder.stopWatching(mockNetworkHandler)

        // then
        verify { mockListener.onStopWatching(any(), mockNetworkHandler) }
    }

    @Test
    fun `tick should invoke tick listeners when attachment is not null`() {
        // given
        val mockAttachment = mockk<HolderAttachment>(relaxed = true)
        val mockListener = mockk<ElementHolderHook.TickListener>()

        every { mockListener.onTick(any()) } just runs

        elementHolder.attachment = mockAttachment
        elementHolderHook.`cornea$addTickListener`(mockListener)

        // when
        elementHolder.tick()

        // then
        verify { mockListener.onTick(any()) }
    }

    @Test
    fun `tick should not invoke tick listeners when attachment is null`() {
        // given
        val mockListener = mockk<ElementHolderHook.TickListener>()

        every { mockListener.onTick(any()) } just runs

        elementHolderHook.`cornea$addTickListener`(mockListener)

        // when
        elementHolder.tick()

        // then
        verify { mockListener wasNot called }
    }
}