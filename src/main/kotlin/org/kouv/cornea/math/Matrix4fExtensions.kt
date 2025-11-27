package org.kouv.cornea.math

import org.joml.*

public inline fun matrix4f(block: Matrix4f.() -> Unit = {}): Matrix4f = Matrix4f().apply(block)

public inline fun matrix4f(mat: Matrix3fc, block: Matrix4f.() -> Unit = {}): Matrix4f = Matrix4f(mat).apply(block)

public inline fun matrix4f(mat: Matrix4fc, block: Matrix4f.() -> Unit = {}): Matrix4f = Matrix4f(mat).apply(block)

public inline fun matrix4f(mat: Matrix4x3fc, block: Matrix4f.() -> Unit = {}): Matrix4f = Matrix4f(mat).apply(block)

public inline fun matrix4f(mat: Matrix4dc, block: Matrix4f.() -> Unit = {}): Matrix4f = Matrix4f(mat).apply(block)

public inline fun matrix4f(
    m00: Float, m01: Float, m02: Float, m03: Float,
    m10: Float, m11: Float, m12: Float, m13: Float,
    m20: Float, m21: Float, m22: Float, m23: Float,
    m30: Float, m31: Float, m32: Float, m33: Float,
    block: Matrix4f.() -> Unit = {}
): Matrix4f = Matrix4f(
    m00, m01, m02, m03,
    m10, m11, m12, m13,
    m20, m21, m22, m23,
    m30, m31, m32, m33
).apply(block)

public inline fun matrix4f(
    col0: Vector4fc,
    col1: Vector4fc,
    col2: Vector4fc,
    col3: Vector4fc,
    block: Matrix4f.() -> Unit = {}
): Matrix4f = Matrix4f(col0, col1, col2, col3).apply(block)