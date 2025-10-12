package org.kouv.cornea.math

import org.joml.*

public inline fun matrix4f(block: Matrix4f.() -> Unit = {}): Matrix4f = Matrix4f().apply(block)

public inline fun matrix4f(from: Matrix3fc, block: Matrix4f.() -> Unit = {}): Matrix4f = Matrix4f(from).apply(block)

public inline fun matrix4f(from: Matrix4fc, block: Matrix4f.() -> Unit = {}): Matrix4f = Matrix4f(from).apply(block)

public inline fun matrix4f(from: Matrix4x3fc, block: Matrix4f.() -> Unit = {}): Matrix4f = Matrix4f(from).apply(block)

public inline fun matrix4f(from: Matrix4dc, block: Matrix4f.() -> Unit = {}): Matrix4f = Matrix4f(from).apply(block)

public fun Matrix4f.rotateXDegrees(angle: Float): Matrix4f = rotateX(Math.toRadians(angle))

public fun Matrix4f.rotateYDegrees(angle: Float): Matrix4f = rotateY(Math.toRadians(angle))

public fun Matrix4f.rotateZDegrees(angle: Float): Matrix4f = rotateZ(Math.toRadians(angle))

public fun Matrix4f.rotateLocalXDegrees(angle: Float): Matrix4f = rotateLocalX(Math.toRadians(angle))

public fun Matrix4f.rotateLocalYDegrees(angle: Float): Matrix4f = rotateLocalY(Math.toRadians(angle))

public fun Matrix4f.rotateLocalZDegrees(angle: Float): Matrix4f = rotateLocalZ(Math.toRadians(angle))