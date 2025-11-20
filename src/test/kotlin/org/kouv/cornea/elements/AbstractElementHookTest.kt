package org.kouv.cornea.elements

import eu.pb4.polymer.virtualentity.api.ElementHolder
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment
import eu.pb4.polymer.virtualentity.api.elements.AbstractElement
import io.mockk.*
import net.minecraft.server.network.ServerPlayNetworkHandler
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.kouv.cornea.holders.elementHolder
import org.kouv.cornea.tests.FabricExtension
import kotlin.test.Test

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

        every { mockListener.onStartWatching(any(), any()) } just runs

        abstractElementHook.`cornea$addStartWatchingListener`(mockListener)

        // when
        elementHolder.startWatching(mockNetworkHandler)

        // then
        verify { mockListener.onStartWatching(any(), any()) }
    }

    @Test
    fun `stopWatching should invoke stopWatching listeners`() {
        // given
        val mockNetworkHandler = mockk<ServerPlayNetworkHandler>(relaxed = true)
        val mockListener = mockk<AbstractElementHook.StopWatchingListener>()

        every { mockListener.onStopWatching(any(), any()) } just runs

        abstractElementHook.`cornea$addStopWatchingListener`(mockListener)
        elementHolder.startWatching(mockNetworkHandler)

        // when
        elementHolder.stopWatching(mockNetworkHandler)

        // then
        verify { mockListener.onStopWatching(any(), any()) }
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