package org.kouv.cornea.tests

import net.minecraft.Bootstrap
import net.minecraft.SharedConstants
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

class FabricExtension :
    BeforeAllCallback,
    BeforeEachCallback,
    AfterEachCallback
{
    private val threadLocal = ThreadLocal<ClassLoader?>()

    override fun beforeAll(context: ExtensionContext) {
        SharedConstants.createGameVersion()
        Bootstrap.initialize()
    }

    override fun beforeEach(context: ExtensionContext) {
        threadLocal.set(Thread.currentThread().contextClassLoader)
        Thread.currentThread().contextClassLoader = Bootstrap::class.java.classLoader
    }

    override fun afterEach(context: ExtensionContext) {
        Thread.currentThread().contextClassLoader = threadLocal.get()
        threadLocal.remove()
    }
}