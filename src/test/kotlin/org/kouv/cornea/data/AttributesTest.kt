package org.kouv.cornea.data

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.kouv.cornea.tests.FabricExtension
import kotlin.test.*

@ExtendWith(FabricExtension::class)
class AttributesTest {
    private lateinit var attributes: Attributes
    private lateinit var keyInt: AttributeKey<Int>
    private lateinit var keyString: AttributeKey<String>

    @BeforeEach
    fun setUp() {
        attributes = Attributes()
        keyInt = AttributeKey("test_int")
        keyString = AttributeKey("test_string")
    }

    @Test
    fun `set and get operators should store and retrieve values correctly`() {
        // given
        val value = 42

        // when
        attributes[keyInt] = value

        // then
        assertTrue(keyInt in attributes)
        assertEquals(value, attributes[keyInt])
    }

    @Test
    fun `keys with same name should be treated as different keys due to IdentityHashMap`() {
        // given
        val key1 = AttributeKey<String>("duplicate_name")
        val key2 = AttributeKey<String>("duplicate_name")

        val value1 = "first"
        val value2 = "second"

        // when
        attributes[key1] = value1
        attributes[key2] = value2

        // then
        assertEquals(value1, attributes[key1])
        assertEquals(value2, attributes[key2])
        assertNotEquals(attributes[key1], attributes[key2])
    }

    @Test
    fun `remove should delete the value associated with the key`() {
        // given
        attributes[keyInt] = 100
        assertTrue(keyInt in attributes)

        // when
        attributes.remove(keyInt)

        // then
        assertFalse(keyInt in attributes)
        assertNull(attributes[keyInt])
    }

    @Test
    fun `contains should return false for missing keys`() {
        // when & then
        assertFalse(keyInt in attributes)
    }
}