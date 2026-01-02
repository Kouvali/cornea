package org.kouv.cornea.math

import org.joml.*

public inline fun quaternionf(block: Quaternionfc.() -> Unit = {}): Quaternionf = Quaternionf().apply(block)

public inline fun quaternionf(
    x: Double,
    y: Double,
    z: Double,
    w: Double,
    block: Quaternionfc.() -> Unit = {}
): Quaternionf = Quaternionf(x, y, z, w).apply(block)

public inline fun quaternionf(
    x: Float,
    y: Float,
    z: Float,
    w: Float,
    block: Quaternionfc.() -> Unit = {}
): Quaternionf = Quaternionf(x, y, z, w).apply(block)

public inline fun quaternionf(source: Quaternionfc, block: Quaternionfc.() -> Unit = {}): Quaternionf =
    Quaternionf(source).apply(block)

public inline fun quaternionf(source: Quaterniondc, block: Quaternionfc.() -> Unit = {}): Quaternionf =
    Quaternionf(source).apply(block)

public inline fun quaternionf(axisAngle: AxisAngle4f, block: Quaternionfc.() -> Unit = {}): Quaternionf =
    Quaternionf(axisAngle).apply(block)

public inline fun quaternionf(axisAngle: AxisAngle4d, block: Quaternionfc.() -> Unit = {}): Quaternionf =
    Quaternionf(axisAngle).apply(block)