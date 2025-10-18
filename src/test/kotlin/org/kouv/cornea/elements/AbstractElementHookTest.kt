package org.kouv.cornea.elements

import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment
import eu.pb4.polymer.virtualentity.api.elements.AbstractElement
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.kouv.cornea.holders.elementHolder
import org.kouv.cornea.tests.FabricExtension
import kotlin.test.Test

@ExtendWith(FabricExtension::class)
class AbstractElementHookTest {
    private lateinit var abstractElement: AbstractElement
    private lateinit var abstractElementHook: AbstractElementHook

    @BeforeEach
    fun setUp() {
        abstractElement = mobAnchorElement()
        abstractElementHook = abstractElement as AbstractElementHook
    }

    @Test
    fun `element tick listeners should be invoked when holder ticks`() {
        // given
        val elementHolder = elementHolder()

        val mockAttachment = mockk<HolderAttachment>(relaxed = true)
        val mockListener = mockk<AbstractElementHook.TickListener>()

        every { mockListener.onTick(any()) } just runs

        elementHolder.addElement(abstractElement)
        elementHolder.attachment = mockAttachment
        abstractElementHook.`cornea$addTickListener`(mockListener)

        // when
        elementHolder.tick()

        // then
        verify { mockListener.onTick(any()) }
    }
}