package org.kouv.cornea.elements

import eu.pb4.polymer.virtualentity.api.elements.DisplayElement
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement
import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector3f
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.kouv.cornea.tests.FabricExtension
import kotlin.test.Test
import kotlin.test.assertEquals

@ExtendWith(FabricExtension::class)
class DisplayElementHookTest {
    private lateinit var displayElement: DisplayElement
    private lateinit var displayElementHook: DisplayElementHook

    @BeforeEach
    fun setUp() {
        displayElement = ItemDisplayElement()
        displayElementHook = displayElement as DisplayElementHook
    }

    @Test
    fun `getTransformation should return identity matrix initially`() {
        // given
        val expected = Matrix4f().identity()

        // when
        val actual = displayElementHook.`cornea$getTransformation`()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `getTransformation should return updated matrix after setting translation`() {
        // given
        val translation = Vector3f(1.0f, 2.0f, 3.0f)
        val expected = Matrix4f().translate(translation)

        // when
        displayElement.translation = translation
        val actual = displayElementHook.`cornea$getTransformation`()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `getTransformation should return updated matrix after setting scale`() {
        // given
        val scale = Vector3f(2.0f, 0.5f, 1.0f)
        val expected = Matrix4f().scale(scale)

        // when
        displayElement.scale = scale
        val actual = displayElementHook.`cornea$getTransformation`()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `getTransformation should return updated matrix after setting left rotation`() {
        // given
        val rotation = Quaternionf().rotateX(Math.toRadians(90.0).toFloat())
        val expected = Matrix4f().rotate(rotation)

        // when
        displayElement.leftRotation = rotation
        val actual = displayElementHook.`cornea$getTransformation`()

        // then
        assertTrue(actual.equals(expected, 1e-6f))
    }

    @Test
    fun `getTransformation should return updated matrix after setting right rotation`() {
        // given
        val rotation = Quaternionf().rotateY(Math.toRadians(45.0).toFloat())
        val expected = Matrix4f().rotate(rotation)

        // when
        displayElement.rightRotation = rotation
        val actual = displayElementHook.`cornea$getTransformation`()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `getTransformation should return cached matrix immediately when setTransformation with matrix is called`() {
        // given
        val matrix = Matrix4f()
            .translate(10f, 0f, 0f)
            .rotateZ(1.5f)
            .scale(2f)

        // when
        displayElement.setTransformation(matrix)
        val actual = displayElementHook.`cornea$getTransformation`()

        // then
        assertEquals(matrix, actual)
    }

    @Test
    fun `getTransformation should calculate complex transformation correctly`() {
        // given
        val translation = Vector3f(1f, 2f, 3f)
        val leftRotation = Quaternionf().rotateZ(Math.toRadians(90.0).toFloat())
        val scale = Vector3f(2f, 2f, 2f)
        val rightRotation = Quaternionf().rotateY(Math.toRadians(45.0).toFloat())

        val expected = Matrix4f()
            .translate(translation)
            .rotate(leftRotation)
            .scale(scale)
            .rotate(rightRotation)

        // when
        displayElement.translation = translation
        displayElement.leftRotation = leftRotation
        displayElement.scale = scale
        displayElement.rightRotation = rightRotation

        val actual = displayElementHook.`cornea$getTransformation`()

        // then
        assertTrue(actual.equals(expected, 1e-6f))
    }

    @Test
    fun `getTransformation should handle dirty flag correctly`() {
        // given
        val matrix1 = Matrix4f().translate(1f, 0f, 0f)
        displayElement.setTransformation(matrix1)

        // then
        assertEquals(matrix1, displayElementHook.`cornea$getTransformation`())

        // when
        val newScale = Vector3f(5f, 5f, 5f)
        displayElement.scale = newScale

        // then
        val expected = Matrix4f().translate(1f, 0f, 0f).scale(newScale)
        assertEquals(expected, displayElementHook.`cornea$getTransformation`())
    }
}